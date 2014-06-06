name := "route-statistics-sample"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  cache,
  "in.aashiks" % "routes-statistics-module_2.10" % "0.3-alpha"
)

play.Project.playJavaSettings
