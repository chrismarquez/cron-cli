import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.1.0"
    application
    id("org.graalvm.buildtools.native") version "0.10.3"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("com.adarshr.test-logger") version "4.0.0"
}

group = "org.voidsynchron"
version = "1.0"

repositories {
    mavenCentral()
}

application {
    mainClass = "org.voidsynchron.MainKt"
}

shadow {
    base {
        archivesName = "cron-cli"
    }
}

tasks.withType<ShadowJar> {
    archiveClassifier = ""
    dependsOn("distTar", "distZip")}


graalvmNative {
    binaries {
        named("main") {
            imageName.set("cron-cli")
        }
    }
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.github.ajalt.clikt:clikt:5.0.1")
    implementation("com.github.ajalt.clikt:clikt-markdown:5.0.1")
}

tasks.test {
    useJUnitPlatform()
}
