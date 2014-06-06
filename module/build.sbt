name := "routes-statistics-module"

organization := "in.aashiks"

organizationName := "AashikS.in"

publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository")))

version := "0.3-alpha"

libraryDependencies ++= Seq(
  cache
)

play.Project.playScalaSettings
