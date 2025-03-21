import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
    id("fabric-loom")
    kotlin("jvm")
    kotlin("plugin.serialization")
}

base {
    val archivesBaseName: String by project
    archivesName.set(archivesBaseName)
}

val fabricKotlinVersion: String by project
val javaVersion = JavaVersion.VERSION_17
val loaderVersion: String by project
val yarnMappings: String by project
val fabricVersion: String by project
val minecraftVersion: String by project
val modId: String by project
val geckolib: String by project
val fstats: String by project
val duckyUpdater: String by project
val modVersion: String by project
version = "${DateTimeFormatter.ofPattern("yyyy.MM").format(LocalDateTime.now())}.$modVersion"

val mavenGroup: String by project
group = mavenGroup

repositories {
    maven("https://cursemaven.com")
    maven("https://api.modrinth.com/maven")
    maven ("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
}

dependencies {
    minecraft("com.mojang", "minecraft", minecraftVersion)

    mappings("net.fabricmc", "yarn", yarnMappings, null, "v2")

    modImplementation("net.fabricmc", "fabric-loader", loaderVersion)

    modImplementation("net.fabricmc.fabric-api", "fabric-api", fabricVersion)

    modImplementation("net.fabricmc", "fabric-language-kotlin", fabricKotlinVersion)
    modImplementation("software.bernie.geckolib:geckolib-fabric-1.19.4:$geckolib")

    include(modImplementation("maven.modrinth", "fstats", fstats))
    include(modImplementation("maven.modrinth", "ducky-updater-lib", duckyUpdater))
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        sourceCompatibility = javaVersion.toString()
        targetCompatibility = javaVersion.toString()
        options.release.set(javaVersion.toString().toInt())
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = javaVersion.toString()
        }
    }

    jar {
        from("LICENSE") {
            rename {
                "${it}_${base.archivesName}"
            }
        }
    }

    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand(
                mutableMapOf(
                    "version" to project.version,
                    "loaderVersion" to loaderVersion,
                    "minecraftVersion" to minecraftVersion,
                    "fabricKotlinVersion" to fabricKotlinVersion,
                    "javaVersion" to javaVersion.toString(),
                    "geckolib" to geckolib,
                    "fstats" to fstats,
                    "duckyUpdater" to duckyUpdater
                )
            )
        }
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(javaVersion.toString()))
        }
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
        withSourcesJar()
    }
}
val generatedResources = "src/generated/resources"

sourceSets.main {
    resources {
        srcDir(generatedResources)
    }
}

loom {
    runs {
        create("Data Generation") {
            client()
            vmArg("-Dfabric-api.datagen")
            vmArg("-Dfabric-api.datagen.output-dir=${file(generatedResources)}")
            vmArg("-Dfabric-api.datagen.modid=${modId}")
            runDir ("build/datagen")
        }
    }
}
