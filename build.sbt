name := """play-emberjs-seed"""
version := "1.0-SNAPSHOT"
scalaVersion := "2.11.8"
organization := "com.dipayan.play"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

PlayKeys.playRunHooks += EmberRunner(streams.value.log, baseDirectory.value / uiAppDirectory.value, uiServerPort.value)

unmanagedResourceDirectories in Assets ++= Seq (
  baseDirectory.value / uiAppDirectory.value / "dist",
  baseDirectory.value / uiAppDirectory.value / "dist" / "assets"
)

(packageBin in Compile) <<= (packageBin in Compile).dependsOn(buildUI)
