import net.thauvin.erik.gradle.semver.SemverIncrementBuildMetaTask
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val logback_version: String by project
val ktor_version: String by project
val kotlin_version: String by project

group = "ch.keepclam"

plugins {
    application
    kotlin("jvm") version "1.3.41"
    id("com.google.cloud.tools.jib") version "1.4.0"
//    id("de.gliderpilot.semantic-release") version "1.4.0"
//    id("org.danilopianini.git-sensitive-semantic-versioning") version "0.2.2"
//    id("net.vivin.gradle-semantic-build-versioning") version "4.0.0"
//    id("de.maltsev.gradle.semanticrelease") version "0.3.4"
//    id("com.github.moleksyuk.vcs-semantic-version") version "1.1.3"
    id("net.thauvin.erik.gradle.semver") version "1.0.4"
    id("com.github.gradle-git-version-calculator") version "1.1.0"
//    id("com.github.ben-manes.versions").version("0.21.0")
}


tasks {
    withType<Test> {
        useTestNG()
    }
    "incrementBuildMeta"(SemverIncrementBuildMetaTask::class) {
        doFirst {
            buildMeta = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
        }
    }
    "run"(JavaExec::class) {
        doFirst {
            println("Version: $version")
        }
        args = listOf("version.properties")
    }
}

task(name = "fooTask"){
    println( "Build: $semver.buildMeta")
}


task(name ="fooBar"){
    println("Hello.... $version")

    println(gitVersionCalculator.calculateVersion("prefix", true))
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