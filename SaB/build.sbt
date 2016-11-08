name := """SaB"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs
)
enablePlugins(PlayEbean)
libraryDependencies += evolutions
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.36"
