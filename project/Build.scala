import sbt._
import Keys._
import play.Project._
import com.typesafe.sbt.SbtNativePackager._
object Build extends sbt.Build {

    val appName         = "helloworldjava"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "be.objectify"  %%  "deadbolt-java"     % "2.2-RC2" exclude("com.typesafe.play", "play-cache_2.10"),
      // Comment this for local development of the Play Authentication core
      "com.feth"      %%  "play-authenticate" % "0.5.2-SNAPSHOT",
      javaCore,
      javaEbean
    )

//  Uncomment this for local development of the Play Authenticate core:
/*
    val playAuthenticate = play.Project(
      "play-authenticate", "1.0-SNAPSHOT", Seq(javaCore, cache), path = file("modules/play-authenticate")
    ).settings(
      libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.2.5",
      libraryDependencies += "com.feth" %% "play-easymail" % "0.5-SNAPSHOT",
      libraryDependencies += "org.mindrot" % "jbcrypt" % "0.3m",
      libraryDependencies += "commons-lang" % "commons-lang" % "2.6",
      libraryDependencies += "com.google.code.morphia" % "morphia" % "0.99",
      
      resolvers += Resolver.url("play-easymail (release)", url("http://joscha.github.com/play-easymail/repo/releases/"))(Resolver.ivyStylePatterns),
      resolvers += Resolver.url("play-easymail (snapshot)", url("http://joscha.github.com/play-easymail/repo/snapshots/"))(Resolver.ivyStylePatterns),
      resolvers += Resolver.url("MavenRepo", url("http://repo1.maven.org/maven2/"))(Resolver.ivyStylePatterns),
	  resolvers += Resolver.url("JavaNet1Repository", url("http://download.java.net/maven/1/"))(Resolver.ivyStylePatterns),
	  resolvers += Resolver.url("GoogleCode", url("http://morphia.googlecode.com/svn/mavenrepo/"))(Resolver.ivyStylePatterns)
      
    )
*/

    val main = play.Project(appName, appVersion, appDependencies).settings(

      resolvers += Resolver.url("Objectify Play Repository (release)", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns),
      resolvers += Resolver.url("Objectify Play Repository (snapshot)", url("http://schaloner.github.com/snapshots/"))(Resolver.ivyStylePatterns),

      resolvers += Resolver.url("play-easymail (release)", url("http://joscha.github.com/play-easymail/repo/releases/"))(Resolver.ivyStylePatterns),
      resolvers += Resolver.url("play-easymail (snapshot)", url("http://joscha.github.com/play-easymail/repo/snapshots/"))(Resolver.ivyStylePatterns),

      resolvers += Resolver.url("play-authenticate (release)", url("http://joscha.github.com/play-authenticate/repo/releases/"))(Resolver.ivyStylePatterns),
      resolvers += Resolver.url("play-authenticate (snapshot)", url("http://joscha.github.com/play-authenticate/repo/snapshots/"))(Resolver.ivyStylePatterns)
    )
//  Uncomment this for local development of the Play Authenticate core:
//    .dependsOn(playAuthenticate).aggregate(playAuthenticate)

}
