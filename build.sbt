name := """reactive-streams-end-to-end-example"""

version := "1.0"

scalaVersion := "2.11.7"

// add conf/ folder
unmanagedResourceDirectories in Compile += baseDirectory.value / "conf"

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xfuture")

libraryDependencies ++= Seq(
  "com.iheart" %% "ficus" % "1.2.2",
  "com.sksamuel.elastic4s" %% "elastic4s-core" % "2.2.0",
  "com.sksamuel.elastic4s" %% "elastic4s-streams" % "2.2.0",
  "com.typesafe.akka" %% "akka-stream" % "2.4.2",
  "com.typesafe.slick" %% "slick" % "3.1.1",
  "org.postgresql" % "postgresql" % "9.4.1208",
  "com.github.tminglei" %% "slick-pg" % "0.11.3",
  "com.github.tminglei" %% "slick-pg_date2" % "0.11.3"
)
