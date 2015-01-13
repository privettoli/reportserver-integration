name := "reportserver-integration"

version := "1.0"

scalaVersion := "2.11.4"

lazy val `reportserver-integration` = (project in file(".")).enablePlugins(
  PlayJava,
  net.litola.SassPlugin
)

libraryDependencies ++= Seq(javaWs,
  "org.springframework"       % "spring-context"                % "latest.integration",
  "javax.inject"              % "javax.inject"                  % "latest.integration",
  "org.projectlombok"         % "lombok"                        % "latest.integration",
  "javax.persistence"         % "persistence-api"               % "latest.integration"
)

javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")