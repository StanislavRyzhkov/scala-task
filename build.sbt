name := "scala-task"

version := "0.1"

scalaVersion := "2.12.12"

//val http4sVersion = "0.21.3"

lazy val common = (project in file("."))
  .enablePlugins(ScalafmtPlugin)
  .enablePlugins(AssemblyPlugin)
  .settings(
    name := "scala-task",
    libraryDependencies ++= Seq(
      "com.typesafe.akka"      %% "akka-http"            % "10.1.10",
      "com.typesafe.akka"      %% "akka-stream"          % "2.6.0",
      "com.typesafe.akka"      %% "akka-actor"           % "2.6.0",
      "com.typesafe.akka"      %% "akka-http-spray-json" % "10.1.10",
      "org.scala-lang.modules" %% "scala-xml"            % "1.2.0",
      "org.jsoup"               % "jsoup"                % "1.12.2"
    ),
    scalacOptions ++= Seq(
      "-feature",
      "-language:implicitConversions",
      "-language:higherKinds",
      "-language:postfixOps"
    ),
    mainClass in (Compile, run) := Some("company.ryzhkov.Application"),
    mainClass in (assembly) := Some("company.ryzhkov.Application"),
    assemblyJarName in assembly := "server.jar"
  )
