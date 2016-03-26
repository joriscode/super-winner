scalaVersion := "2.11.8"

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:experimental.macros",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Xexperimental",
  "-Xfuture",
  "-Xlint",
  "-Ybackend:GenBCode",
  "-Ydelambdafy:method",
  "-Yinline-warnings",
  "-Yno-adapted-args",
  "-Yrangepos",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard"
)

libraryDependencies ++= Seq(
  "com.github.nscala-time" %% "nscala-time"                       % "2.10.0",
  "com.typesafe.akka"      %% "akka-http-experimental"            % "2.4.2",
  "com.typesafe.akka"      %% "akka-http-spray-json-experimental" % "2.4.2",
  "com.propensive"         %% "rapture-xml"                       % "2.0.0-M5",
  "com.lihaoyi"            %% "pprint"                            % "0.3.8"

)

enablePlugins(ScalaKataPlugin)
securityManager in Backend := false