package docs

import java.nio.file.Paths

object Main {
  def main(args: Array[String]): Unit = {
    val settings = mdoc
      .MainSettings()
      .withOut(Paths.get("website", "target", "docs"))
      .withSiteVariables(
        Map(
          "VERSION" -> BuildInfo.scalameta
        )
      )
      .withArgs(args.toList)
    val exit = mdoc.Main.process(settings)
    sys.exit(exit)
  }
}
