.. raw:: html

   <div>

.. raw:: html

   <div id="Scalameta"
   class="scalatex-site-Styles-hoverContainer scalatex-site-Styles-headerTag"
   style="margin: 0px;color: #333;text-align: center;padding: 2.5em 2em 0;border-bottom: 1px solid #eee;display: block;">

.. rubric:: Scalameta\ `** <#Scalameta>`__
   :name: scalameta

.. rubric:: 2.0.0-RC1 (released on 02 Sep 2017)
   :name: rc1-released-on-02-sep-2017

.. raw:: html

   </div>

.. raw:: html

   <div class="scalatex-site-Styles-content">

Scalameta is a modern metaprogramming library for Scala that supports a
wide range of language versions and execution platforms. Originally,
Scalameta was founded to become a better macro system for Scala, but
over time we shifted focus to developer tools and spun off the new macro
system into a `separate
project <https://github.com/scalamacros/scalamacros>`__.

.. raw:: html

   <div id="Features"
   class="scalatex-site-Styles-hoverContainer scalatex-site-Styles-headerTag"
   style="margin: 0px;color: #333;text-align: center;padding: 2.5em 2em 0;border-bottom: 1px solid #eee;display: block;">

.. rubric:: Features\ `** <#Features>`__
   :name: features

.. raw:: html

   </div>

**High-fidelity parsing.** Note how the abstract syntax trees in the
printout below contain comprehensive information about formatting and
comments. This is an exclusive feature of Scalameta.

::

    scala> "x + y /* adds x and y */".parse[Term]
    res0: scala.meta.parsers.Parsed[scala.meta.Term] = x + y /* adds x and y */

    scala> "List[ Int ]".parse[Type]
    res1: scala.meta.parsers.Parsed[scala.meta.Type] = List[ Int ]

**Tokens.** Scalameta takes even the finest details of Scala code into
account. We achieve this by attaching tokens, data structures
representing atomic units of Scala syntax, to our abstract syntax trees.
Note that the abstract syntax tree in the printout doesn't have the
comment per se - it is stored in tokens instead.

::

    scala> val tree = "x + y /* adds x and y */".parse[Term].get
    tree: scala.meta.Term = x + y /* adds x and y */

    scala> tree.syntax
    res0: String = x + y /* adds x and y */

    scala> tree.structure
    res1: String = Term.ApplyInfix(Term.Name("x"), Term.Name("+"), Nil, List(Term.Name("y")))

    scala> tree.tokens.structure
    res2: String = Tokens(BOF [0..0), x [0..1),   [1..2), + [2..3),   [3..4), y [4..5),   [5..6), /* adds x and y */ [6..24), EOF [24..24))

**Quasiquotes.** Quasiquotes have proven to be an amazing productivity
booster in scala.reflect, so we implemented them in Scalameta and now
they are better than ever. Note the precise types for ``x`` and ``y``
prevent the programmer from generating invalid code. Learn more about
supported quasiquotes features
`here <https://github.com/scalameta/scalameta/blob/master/notes/quasiquotes.md>`__.

::

    scala> val addition = q"x + y"
    addition: meta.Term.ApplyInfix = x + y

    scala> val q"$x + $y" = addition
    x: scala.meta.Term = x
    y: scala.meta.Term = y

    scala> q"def y: $x"
    <console>:19: error: type mismatch when unquoting;
     found   : scala.meta.Term
     required: scala.meta.Type
           q"def y: $x"
                    ^

**Dialects.** Scalameta is designed from the ground up to understand
different versions of the base language: Scala 2.10, Scala 2.11, Scala
2.12 and even Dotty. We also support Sbt build files to make sure we
cover as much Scala code as possible.

::

    scala> import scala.meta.dialects.Sbt0137
    import scala.meta.dialects.Sbt0137

    scala> Sbt0137("""
      lazy val root = (project in file(".")).
      settings(name := "hello")
    """).parse[Source]
    res0: scala.meta.parsers.Parsed[scala.meta.Source] =

      lazy val root = (project in file(".")).
      settings(name := "hello")

+--------------+-----------+----------------+--------------------------------------------------------------------------+
| Dialect      | Syntax    | Semantic       | Notes                                                                    |
+==============+===========+================+==========================================================================+
| Scala 2.10   | Yes       | Yes\*          | Requires `an external module <https://github.com/scalameta/sbthost>`__   |
+--------------+-----------+----------------+--------------------------------------------------------------------------+
| Scala 2.11   | Yes       | Full support   |                                                                          |
+--------------+-----------+----------------+--------------------------------------------------------------------------+
| Scala 2.12   | Yes       | Full support   |                                                                          |
+--------------+-----------+----------------+--------------------------------------------------------------------------+
| Dotty        | Partial   | No             | Partial support for new language features.                               |
+--------------+-----------+----------------+--------------------------------------------------------------------------+
| Sbt 0.13     | Yes       | Yes\*          | Requires `an external module <https://github.com/scalameta/sbthost>`__   |
+--------------+-----------+----------------+--------------------------------------------------------------------------+
| Sbt 1.0      | Yes       | No             |                                                                          |
+--------------+-----------+----------------+--------------------------------------------------------------------------+

**Semantic API.** Semantic analysis of Scala programs has typically been
tricky business, requiring intimate familiarity with compiler internals.
Scalameta involves none of that. Our semantic API is powered by
`Semantic DB <http://scalameta.org/tutorial/#SemanticDB>`__, a simple
`schema <https://github.com/scalameta/scalameta/blob/master/langmeta/semanticdb/shared/src/main/protobuf/semanticdb.proto>`__
for persisting semantic information extracted from the Scala compiler.
Saved semantic information can then be used in multiple applications
running on different platforms, potentially in a distributed fashion.

+----------------------------+--------------+
| Feature                    | Supported?   |
+============================+==============+
| Compiler messages          | Yes          |
+----------------------------+--------------+
| Implicit arguments         | Yes          |
+----------------------------+--------------+
| Implicit conversions       | Yes          |
+----------------------------+--------------+
| Inferred .apply/.unapply   | Yes          |
+----------------------------+--------------+
| Inferred type arguments    | Yes          |
+----------------------------+--------------+
| Symbol at position         | Yes          |
+----------------------------+--------------+
| Symbol signature           | Yes          |
+----------------------------+--------------+
| Auto completion            | No           |
+----------------------------+--------------+
| Macro expansions           | No           |
+----------------------------+--------------+
| Type at position           | No           |
+----------------------------+--------------+
| Type members               | No           |
+----------------------------+--------------+

**Cross-platform.** Scalameta is available as a JVM library and as a
JavaScript module via `Scala.js <http://www.scala-js.org/>`__. Support
for a native library via `Scala Native <http://www.scala-native.org/>`__
is `in the works <https://github.com/scalameta/scalameta/issues/772>`__.

.. raw:: html

   <div id="BuiltwithScalameta"
   class="scalatex-site-Styles-hoverContainer scalatex-site-Styles-headerTag"
   style="margin: 0px;color: #333;text-align: center;padding: 2.5em 2em 0;border-bottom: 1px solid #eee;display: block;">

.. rubric:: Built with Scalameta\ `** <#BuiltwithScalameta>`__
   :name: built-with-scalameta

.. raw:: html

   </div>

Feel free to add your project to this list.

-  `Scalafmt <http://scalameta.org/scalafmt/>`__: code formatter.
-  `Scalafix <https://scalacenter.github.io/scalafix/>`__: automated
   code rewriter.
-  `Metadoc <http://scalameta.org/metadoc/>`__: online code browser with
   "Jump to definition" and "See references".
-  `Imclipitly <https://github.com/ShaneDelmore/imclipitly>`__:
   Scala-Clippy advice generator for implicit enrichments.
-  `Stags <https://github.com/pjrt/stags>`__: Scala tags generator.
-  `AST
   explorer <https://astexplorer.net/#/gist/22cf8a3fcb2155c087ae94b4d194c1b6/d10c646ecfae4c69c919408aa3aaefb2deda2df7>`__:
   interactive explorer of Scala syntax trees.
-  `Metarpheus <https://blog.buildo.io/metarpheus-a-custom-approach-to-api-contracts-f340a6792d43>`__:
   extract models and apis from a spray-based server.

.. raw:: html

   <div id="Releases"
   class="scalatex-site-Styles-hoverContainer scalatex-site-Styles-headerTag"
   style="margin: 0px;color: #333;text-align: center;padding: 2.5em 2em 0;border-bottom: 1px solid #eee;display: block;">

.. rubric:: Releases\ `** <#Releases>`__
   :name: releases

.. raw:: html

   </div>

**Train model.** Every six weeks, we publish a release with the latest
changes.

**Semantic versioning.** With Scalameta 2.0 onwards, we follow `semantic
versioning <http://semver.org/>`__ enforced with
`MiMa <https://github.com/typesafehub/migration-manager/>`__. Binary
breaking changes bump up the major version (e.g., 2.0 -> 3.0), binary
compatible improvements bump up the minor version (e.g., 2.0 -> 2.1).

**Milestone releases.** At any point in the release cycle, we may cut
milestone releases to test out work-in-progress changes.

.. raw:: html

   <div id="Gettingstarted"
   class="scalatex-site-Styles-hoverContainer scalatex-site-Styles-headerTag"
   style="margin: 0px;color: #333;text-align: center;padding: 2.5em 2em 0;border-bottom: 1px solid #eee;display: block;">

.. rubric:: Getting started\ `** <#Gettingstarted>`__
   :name: getting-started

.. raw:: html

   </div>

To get started with scalameta, add the following to your ``build.sbt``:

::

    // Latest stable version
    libraryDependencies += "org.scalameta" %% "scalameta" % "2.0.0-RC1"

Next, you'll need to add a single wildcard import to the files where
you'll be using scalameta.

::

    import scala.meta._

.. raw:: html

   <div id="Tutorial"
   class="scalatex-site-Styles-hoverContainer scalatex-site-Styles-headerTag"
   style="margin: 0px;color: #333;text-align: center;padding: 2.5em 2em 0;border-bottom: 1px solid #eee;display: block;">

.. rubric:: Tutorial\ `** <#Tutorial>`__
   :name: tutorial

.. raw:: html

   </div>

To learn more about practical aspects of using scalameta, take a look at
our tutorial that is based on a workshop given by Ólafur Pall Geirsson
at Scala World 2016: http://scalameta.org/tutorial.

.. raw:: html

   <div id="Roadmap"
   class="scalatex-site-Styles-hoverContainer scalatex-site-Styles-headerTag"
   style="margin: 0px;color: #333;text-align: center;padding: 2.5em 2em 0;border-bottom: 1px solid #eee;display: block;">

.. rubric:: Roadmap\ `** <#Roadmap>`__
   :name: roadmap

.. raw:: html

   </div>

**Refactoring.** `Scalafix <https://scalacenter.github.io/scalafix/>`__
is a code rewriting tool developed at the `Scala
Center <https://scala.epfl.ch/>`__. Scalafix rewrites use the Scalameta
to automate migration between different library and compiler versions.

**Scala Native support.** Slow startup time for JVM command-line tools
is a big blocker for many exciting editor integrations, such as code
formatting. Scala Native opens possiblities to implement command-line
tools that run in milliseconds instead of seconds. We have validated
that scalafmt can run on native, see
`tweet <https://twitter.com/olafurpg/status/857559907876433920>`__, we
"just" need to get our tests ported to know it works as expected. See
https://github.com/scalameta/scalameta/issues/772.

.. raw:: html

   <div id="Notontheroadmap"
   class="scalatex-site-Styles-hoverContainer scalatex-site-Styles-headerTag"
   style="margin: 0px;color: #333;text-align: center;padding: 2.5em 2em 0;border-bottom: 1px solid #eee;display: block;">

.. rubric:: Not on the roadmap\ `** <#Notontheroadmap>`__
   :name: not-on-the-roadmap

.. raw:: html

   </div>

**Scala macros.** Originally, Scalameta was founded to become a better
macro system for Scala, but over time we shifted focus to developer
tools and spun off the new macro system into a `separate
project <https://github.com/scalamacros/scalamacros>`__.

.. raw:: html

   <div id="Talks"
   class="scalatex-site-Styles-hoverContainer scalatex-site-Styles-headerTag"
   style="margin: 0px;color: #333;text-align: center;padding: 2.5em 2em 0;border-bottom: 1px solid #eee;display: block;">

.. rubric:: Talks\ `** <#Talks>`__
   :name: talks

.. raw:: html

   </div>

**Semantic Tooling at Twitter** (ScalaDays Copenhagen 2017). This talk
introduces semantic databases, the cornerstone of the scalameta semantic
API, and explains how semantic databases can be used to integrate with
Kythe, a language-agnostic ecosystem for developer tools. In this talk,
we presented our vision of next-generation semantic tooling for the
Scala ecosystem.

| Video: https://www.youtube.com/watch?v=4yqDFsdKciA
| Slides:
  http://scalameta.org/talks/2017-06-01-SemanticToolingAtTwitter.pdf

**Metaprogramming 2.0** (ScalaDays Berlin 2016). This talk explains the
status of scalameta, demonstrates key features, presents the early
adopters and publishes our plans for the future. The centerpiece of the
talk is the demo of a new macro system for Scala, which is no longer
part of Scalameta. Nonetheless, the talk still does a good job of
showcasing potential usecases for Scalameta and highlighting
contributions from our amazing community.

| Video: https://www.youtube.com/watch?v=IPnd_SZJ1nM
| Slides:
  http://scalamacros.org/paperstalks/2016-06-17-Metaprogramming20.pdf

.. raw:: html

   <div id="Supporters"
   class="scalatex-site-Styles-hoverContainer scalatex-site-Styles-headerTag"
   style="margin: 0px;color: #333;text-align: center;padding: 2.5em 2em 0;border-bottom: 1px solid #eee;display: block;">

.. rubric:: Supporters\ `** <#Supporters>`__
   :name: supporters

.. raw:: html

   </div>

|image0|     |image1|     |image2|     |image3|     |image4|

.. raw:: html

   </div>

.. raw:: html

   <div
   style="margin: 0px;color: #ccc;text-align: center;padding: 0.5em 2em 0.5em 0em;border-top: 1px solid #eee;display: block;">

(c) 2014 - 2017 scalameta contributors

.. raw:: html

   </div>

|Fork me on GitHub|

.. raw:: html

   </div>

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               

.. |image0| image:: images/twitter.png
   :width: 112px
   :height: 90px
   :target: https://twitter.com/
.. |image1| image:: images/jetbrains.png
   :width: 95px
   :height: 95px
   :target: https://www.jetbrains.com/
.. |image2| image:: images/codacy.png
   :width: 105px
   :height: 105px
   :target: https://www.codacy.com/
.. |image3| image:: images/scala_center.png
   :width: 70px
   :height: 100px
   :target: https://scala.epfl.ch/
.. |image4| image:: images/evolution_gaming.png
   :width: 75px
   :height: 95px
   :target: https://www.evolutiongaming.com/
.. |Fork me on GitHub| image:: https://camo.githubusercontent.com/652c5b9acfaddf3a9c326fa6bde407b87f7be0f4/68747470733a2f2f73332e616d617a6f6e6177732e636f6d2f6769746875622f726962626f6e732f666f726b6d655f72696768745f6f72616e67655f6666373630302e706e67
   :target: https://github.com/scalameta/scalameta
