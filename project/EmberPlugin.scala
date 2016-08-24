import sbt.{Process, _}


object EmberPlugin extends sbt.AutoPlugin {
  override def trigger = allRequirements

  object autoImport {
    val uiServerPort          = settingKey[Int]("Defines the port which runs ember server in development mode.")
    val uiAppDirectory    = settingKey[String]("Defines the base directory which has the ui app.")
    val buildUI               = taskKey[Unit]("Build the ember application in production mode")
  }

  import autoImport._

  override def projectSettings: Seq[Def.Setting[_]] = Seq (
    uiServerPort := 4200,
    uiAppDirectory := "ui",
    buildUI := { buildEmberApplication(Keys.baseDirectory.value / uiAppDirectory.value, Keys.streams.value.log) }
  )

  def buildEmberApplication(uiDirectory: File, logger: Logger) = {
    logger.info("Building ember application in production mode")
    val npm = Process(Seq("ember", "build", "--environment=production"), uiDirectory).run(logger)
    logger.info(s"Ember application build with exit code ${npm.exitValue()}")
  }
}