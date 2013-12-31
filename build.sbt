name := "helloworldjava"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
"net.vz.mongodb.jackson" %% "play-mongo-jackson-mapper" % "1.1.0"
) 

val appDependencies = Seq(
	javaJpa,
    jdbc,
    javaCore,
    javaJdbc
)

play.Project.playJavaSettings
