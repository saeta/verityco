name := "verityco"

version := "1.0"

scalaVersion := "2.9.1"

resolvers +=  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "com.typesafe.akka" % "akka-actor" % "2.0.1"

libraryDependencies += "org.ow2.asm" % "asm-parent" % "4.0"

libraryDependencies += "org.specs2" %% "specs2" % "1.10" % "test"

