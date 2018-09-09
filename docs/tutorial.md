---
id: guide
sidebar_label: Guide
title: Scalameta Guide
---

Scalameta is a library to read, analyze, transform and generate Scala programs.
In this document, you will learn how to use Scalameta to

- parse source code into syntax trees
- construct syntax trees with quasi-quotes and normal constructors
- traverse syntax trees
- transform syntax trees

Let's get started!

## Installation

Scalameta is a library that you depend on in your build. Scalameta supports the
Scala versions 2.11 and Scala 2.12 and runs on the JVM,
[Scala.js](http://www.scala-js.org/) and
[Scala Native](http://www.scala-native.org/).

### sbt

```scala
// build.sbt
libraryDependencies += "org.scalameta" %% "scalameta" % "@VERSION@"

// For Scala.js, Scala Native
libraryDependencies += "org.scalameta" %%% "scalameta" % "@VERSION@"
```

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.scalameta/scalameta_2.12/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.scalameta/scalameta_2.12)

All of the examples in this document assume you have the following import

```scala mdoc:silent
import scala.meta._
```

### Ammonite REPL

A great way to experiment Scalameta is to use the
[Ammonite REPL](http://ammonite.io/#Ammonite-REPL).

```scala
// Ammonite REPL
import $ivy.`org.scalameta::scalameta:@VERSION@`, scala.meta._
```

### Scastie

You can try out Scalameta online with the [Scastie playground](scastie.md).

## Parse trees from source code

You can parse source code from a string

```scala mdoc
val programString = "a + b"
val tree = programString.parse[Term].get
```

You can also parse source code from a file

```scala mdoc
import java.nio.file._
val programFile = Files.createTempFile("scalameta", "program.scala")
Files.write(programFile, "a + b".getBytes())
val treeFromFile = programFile.parse[Term].get
```

## Walk down trees

Use `.traverse` to visit every tree node without collecting values, similarly to
`.foreach`

```scala mdoc
tree.traverse {
  case node =>
    println(s"${node.productPrefix}: $node")
}
```

Use `.collect` to visit every tree node and collect a computed value for
intersting tree nodes

```scala mdoc
tree.collect {
  case Term.Name(name) =>
    name
}
```

Use `.transform` to change the shape of the tree

```scala mdoc
tree.transform {
  case Term.Name(name) =>
    Term.Name(name.toUpperCase)
}.toString
```

The methods `traverse`, `collect` and `transform` don't allow you to customize
the recursion of the tree traversal. For more fine-grained control you can
implement custom `Traverser` and `Transformer` instances.

A `Traverser` implements a `Tree => Unit` function

```scala mdoc
new Traverser {
  override def apply(tree: Tree): Unit = tree match {
    case infix: Term.ApplyInfix =>
      println(infix.op)
    case _ =>
      super.apply(tree)
  }
}.apply(tree)
```

A `Transformer` implements a `Tree => Tree` function

```scala mdoc
new Transformer {
  override def apply(tree: Tree): Tree = tree match {
    case Term.Name(name) =>
      Term.Name(name.toUpperCase)
    case Term.ApplyInfix(lhs, op, targs, args) =>
      Term.ApplyInfix(
          this.apply(lhs).asInstanceOf[Term],
          op, targs, args
      )
    case _ =>
      super.apply(tree)
  }
}.apply(tree).toString
```

## Construct trees with quasi-quotes

Quasi-quotes are a simple way to construct tree nodes

```scala mdoc
val quasiquote = q"a + b"
quasiquote.collect { case Term.Name(name) => name }
```

Quasi-quotes expand at compile-time into direct calls to tree constructors. The
quasi-quote above is equivalent to the manually written `Term.Apply(...)`
expression below

```scala mdoc
val noQuasiquote = Term.ApplyInfix(Term.Name("a"), Term.Name("+"), List(), List(Term.Name("b")))
noQuasiquote.toString
```

## Compare trees for equality

Trees use reference equality by default. This may seem counter intuitive at
