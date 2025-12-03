import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

// add project file

plugins {
    id("fabric-loom") version "1.9-SNAPSHOT"
    `maven-publish`
    // shadow
    id("com.gradleup.shadow") version "9.2.0"
    kotlin("jvm")
}

version = property("mod_version") as String
group = property("maven_group") as String

base {
    archivesName.set(property("archives_base_name") as String)
}

repositories {
    maven {
        url = uri("https://maven.bawnorton.com/releases")
    }
    mavenCentral()
}


tasks.withType<ShadowJar> {
    relocate("com.bawnorton.mixinsquared", "com.github.yiranmushroom.antianticheat_dangerous_world.mixinsquared")
    mergeServiceFiles()
}

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:${property("loader_version")}")

    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabric_version")}")

    modImplementation(files("target/AliveAndWell-mc1.19.2-fabric-2.5.18-modrinth.jar"))
    compileOnly("com.github.bawnorton.mixinsquared:mixinsquared-fabric:0.3.7-beta.1")
    annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-fabric:0.3.7-beta.1")
    shadow(annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-fabric:0.3.7-beta.1")!!)
    include(implementation(annotationProcessor("io.github.llamalad7:mixinextras-fabric:0.5.0")!!)!!)
    modImplementation("net.fabricmc:fabric-language-kotlin:1.10.8+kotlin.1.9.0")
}

tasks {
    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand(getProperties())
            expand(mutableMapOf("version" to project.version))
        }
    }

    shadowJar {
        configurations = listOf(project.configurations.shadow.get())
        archiveClassifier.set("dev-shadow")

        relocate("com.bawnorton.mixinsquared", "com.github.yiranmushroom.antianticheat_dangerous_world.mixinsquared")
        mergeServiceFiles()
    }

    jar {
        enabled = false
    }

    remapJar {
        dependsOn(shadowJar)

        inputFile.set(shadowJar.flatMap { it.archiveFile })

        archiveClassifier.set("")
    }

    build {
        dependsOn(remapJar)
    }
}

loom {
    accessWidenerPath = file("src/main/resources/aacfdw.accessWidener")
}

java {
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()
}
kotlin {
    jvmToolchain(17)
}