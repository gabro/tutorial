+++
title = "Scalameta"
description = "Cross-platform metaprogramming toolkit for Scala"
+++

# Scalameta


Scalameta is a clean-room implementation of a metaprogramming toolkit for Scala, designed to be simple, robust and portable. We are striving for scalameta to become a successor of scala.reflect, the current de facto standard in the Scala ecosystem.

Scalameta provides functionality that's unprecedented in the Scala ecosystem. Our killer feature is abstract syntax trees that capture the code exactly as it is written - with all the original formatting and attention to minor syntactic details.

With scalameta, we are building a community of next-generation tooling for
Scala. [Codacy][]'s Scala engine, [Scalafmt][], and [Scalafix][] take advantage of our
unique features and deliver user experiences that have been unreachable for the
most of the traditional Scala tools.

[Codacy]: https://codacy.com/
[Scalafmt]: http://scalafmt.org
[Scalafix]: https://scalacenter.github.io/scalafix/

## Features

**Simple setup**.
Unlike with scala.reflect, the metaprogramming library from the standard
distribution, no complicated setup is necessary to start using scalameta.

```tut:silent
import scala.meta._
```

**High-fidelity parsing.**
Note how the abstract syntax trees in the printout below contain comprehensive
information about formatting and comments. This is an exclusive feature of
scalameta.

```tut
"x + y /* adds x and y */".parse[Term]
"List[ Int ]".parse[Type]
```

**Tokens.**
Scalameta takes even the finest details of Scala code into account. We achieve
this by attaching tokens, data structures representing atomic units of Scala
syntax, to our abstract syntax trees. Note that the abstract syntax tree in the
printout doesn't have the comment per se - it is stored in tokens instead.

```tut
val tree = "x + y /* adds x and y */".parse[Term].get
tree.syntax
tree.structure
tree.tokens.structure
```

**Dialects.**
Scalameta is designed from the ground up to be platform-independent. This means
that we understand different versions of the base language: Scala 2.10, Scala
2.11 and even Dotty. We also support Sbt build files to make sure we cover as
much Scala code as possible.

```tut
import scala.meta.dialects.Sbt0137
Sbt0137("""
  lazy val root = (project in file(".")).
  settings(name := "hello")
""").parse[Source]
```

**Quasiquotes.**
Scala.reflect is notorious for being obscure and user-unfriendly, but it has
its moments. Quasiquotes have proven to be an amazing productivity booster, so
we implemented them in scalameta, and now they are better than ever. Note how
the precise types for x and y prevent the programmer from generating invalid
code. Learn more about quasiquotes in our documentation.

```tut
val addition = q"x + y"
val q"$x + $y" = addition
q"$y + $x"
```


[quasiquotes]: https://github.com/scalameta/scalameta/blob/master/notes/quasiquotes.md

## Releases

**Stable releases.**
We are committed to publishing major releases (e.g. 1.7.0, 1.8.0, etc) on the
10th day of every month. In such releases, we accumulate battle-tested changes
that have been merged since the last release and make them available on Maven
Central.

**Pre-release builds.**
We automatically publish pre-release builds to our Bintray repository on every
merge into master, typically within 10-30 minutes of a merge. In such builds,
we publish potentially experimental changes that haven't yet undergone
practical testing.

**Compatibility.**
Following a popular convention, we only provide binary and source compatibility
between minor versions (e.g. between 1.6.0 and 1.6.1, but not between 1.6.0 and
1.7.0). There are no compatibility guarantees provided for pre-release builds.


## Getting Started

To get started with the latest stable release of scalameta, add the following
to your `build.sbt`:

```scala
// Latest stable version
libraryDependencies += "org.scalameta" %% "scalameta" % "1.8.0"
```


## Roadmap

**Semantic API.**
Our first priority is to come up with an API that will provide functionality to
perform typechecking, name resolution, implicit inference, etc. It is crucial
to fully model the language and achieve feature parity with scala.reflect.
Visit #604 to check out our roadmap and provide feedback.

**New-style ("inline") macros.**
As announced at ScalaDays New York City 2016, macros based on scala.reflect are
going to be dropped from the future versions of Scala. We are now working on
the replacement that is designed to be platform-independent and easy-to-use.
You may be interested in visiting https://github.com/scalamacros/scalamacros to
check out the state of the art.

**Other execution environments.**
The current version of scalameta can only be run with Scala 2.11, Scala 2.12 or
Scala.js. This means that it is very hard or outright impossible to write
scalameta-based tools targetting Scala 2.10 (e.g. running in sbt 0.13.x) or
Scala Native. Vote for #295 and #772 on our issue tracker if that's important
for you.

## Talks

**Semantic Tooling at Twitter** (ScalaDays Copenhagen 2017).
This talk introduces semantic databases, the cornerstone of the scalameta
semantic API, and explains how semantic databases can be used to integrate with
Kythe, a language-agnostic ecosystem for developer tools. In this talk, we
presented our vision of next-generation semantic tooling for the Scala
ecosystem.

Slides: http://scalameta.org/talks/2017-06-01-SemanticToolingAtTwitter.pdf

**Metaprogramming 2.0** (ScalaDays Berlin 2016).
This talk explains the status of scalameta, demonstrates key features, presents
the early adopters and publishes our plans for the future. In Berlin, we
provided an extensive story of what's going to happen to compile-time
metaprogramming in Scala, featuring a live demo of new-style ("inline") macros
that support integration with IntelliJ IDEA.

Video: https://www.youtube.com/watch?v=IPnd_SZJ1nM 

Slides: http://scalamacros.org/paperstalks/2016-06-17-Metaprogramming20.pdf

