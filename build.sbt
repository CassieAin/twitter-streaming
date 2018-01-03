name := "emoji-twitter-akka"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "org.twitter4j" % "twitter4j-stream" % "4.0.6",
  "com.vdurmont" % "emoji-java" % "4.0.0",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2",
  "com.typesafe.akka" %% "akka-stream" % "2.5.8",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.8" % Test,
  "com.typesafe.akka" %% "akka-slf4j" % "2.5.8",
  "com.typesafe.akka" %% "akka-http" % "10.0.11",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.0.11" % Test,
  "net.debasishg" %% "redisclient" % "3.4",
  "net.codingwell" %% "scala-guice" % "4.1.0",
  "com.typesafe.slick" %% "slick" % "3.2.1",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.1",
  "org.postgresql" % "postgresql" % "42.1.4"
)