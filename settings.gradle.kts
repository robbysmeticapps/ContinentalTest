pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        // <-- add this block
        id("com.google.dagger.hilt.android") version "2.56.2" apply false
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://continentalits.jfrog.io/continentalits/android-kaas")
            credentials {
                username = "ENTER_USERNAME"
                password = "ENTER_PASSWORD"
            }
        }
    }
}

rootProject.name = "ContinentalTest"
include(":app")