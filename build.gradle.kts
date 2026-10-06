plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.10.4"
}

group = providers.gradleProperty("pluginGroup").get()
version = providers.gradleProperty("pluginVersion").get()

repositories {
    mavenCentral()

    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        intellijIdea(
            providers.gradleProperty("platformVersion").get()
        )

        bundledPlugin("com.intellij.java")

    }
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = "253"
        }

        changeNotes = """
            Version 1.0.0:
            - Added long method inspection.
            - Added deep nesting inspection.
            - Added duplicate string inspection.
        """.trimIndent()
    }
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "21"
        targetCompatibility = "21"
    }
}
