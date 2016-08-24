import java.io.File
import java.net.InetSocketAddress

import play.sbt.PlayRunHook
import sbt._

object EmberRunner {
  def apply(logger: Logger,
            baseDir: File,
            port: Int = 4200): PlayRunHook = {

    object EmberRunHook extends PlayRunHook {
      var watchProcess: Option[Process] = None

      override def beforeStarted(): Unit = {
        if (baseDir.exists()) {
          logger.info("Executing npm install")
          val npm = Process(Seq("npm", "install"), baseDir).lines
          npm.foreach(logger.info(_))

          logger.info("Executing bower install")
          val bower = Process(Seq("bower", "install"), baseDir).lines
          bower.foreach(logger.info(_))
        } else {
          logger.info(s"Skipping npm and bower install. UI application directory ${baseDir.getAbsolutePath} not found.")
        }
      }

      override def afterStarted(addr: InetSocketAddress): Unit = {
        addr.getAddress.getHostAddress
        val url = s"http://localhost:${addr.getPort}"
        if (baseDir.exists()) {
          logger.info(s"Starting ember server in development mode. Setting proxy to $url")
          watchProcess = Some(Process(Seq("ember", "serve", "--proxy", url, "--port", port.toString), baseDir).run(logger))
        } else {
          logger.info(s"Skipping ember server start. UI application directory ${baseDir.getAbsolutePath} not found.")
        }
      }

      override def afterStopped(): Unit = {
        logger.info("Attempting to stop ember server")
        watchProcess.foreach(_.destroy())
        watchProcess = None
      }
    }
    EmberRunHook
  }
}