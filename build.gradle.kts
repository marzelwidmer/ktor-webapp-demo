import java.util.regex.Pattern

val logback_version: String by project
val ktor_version: String by project
val kotlin_version: String by project

group = "ch.keepclam"

plugins {
    application
    kotlin("jvm") version "1.3.41"
    id("com.google.cloud.tools.jib") version "1.4.0"
    id("pl.allegro.tech.build.axion-release") version "1.10.1"
}

project.version = scmVersion.version

scmVersion {
     releaseBranchPattern =  Pattern.compile("(/.*)?$")

//    tag {
//        prefix = "my-project-name"
//    }
//    versionCreator "versionWithBranch"
//
//    hooks {
//        pre "fileUpdate", [file: 'README.md', pattern: {v,p -> /(version.) $v/}, replacement: {v, p -> "\$1 $v"}]
//        pre "commit"
//    }
}

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
    applicationName = "$rootProject.name"
    version = "$version"
    group = "$group"
}

repositories {
    mavenLocal()
    jcenter()
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    compile("io.ktor:ktor-server-netty:$ktor_version")
    compile("ch.qos.logback:logback-classic:$logback_version")
    compile("io.ktor:ktor-server-core:$ktor_version")
    compile("io.ktor:ktor-freemarker:$ktor_version")
    compile("io.ktor:ktor-server-host-common:$ktor_version")
    // Test
    testCompile("io.ktor:ktor-server-tests:$ktor_version")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")
kotlin.experimental

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")

// Jib Plugin
jib.from.image = "openjdk:8-jre-alpine"
jib.to.image = "c3smonkey/ktor-webapp:$version"  // Used for docker hub
jib.container.jvmFlags = listOf("-Djava.security.egd=file:/dev/./urandom")
jib.container.ports = listOf("8080")
jib.container.user = "1000320000"