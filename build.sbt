def scalameta = "4.0.0-M11"
def scala212 = "2.12.6"

inThisBuild(
  List(
    organization := "org.scalameta",
    scalaVersion := scala212,
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.1" % Test
    ),
    resolvers += Resolver.sonatypeRepo("releases")
  )
)

name := "scalameta-tutorial"
skip in publish := true

lazy val docs = project
  .in(file("scalameta-docs"))
  .settings(
    moduleName := "scalameta-docs",
    mainClass.in(Compile) := Some("docs.Main"),
    libraryDependencies ++= List(
      "org.scalameta" %% "testkit" % scalameta
    )
  )
  .enablePlugins(DocusaurusPlugin)
