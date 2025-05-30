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
        maven {
            url = uri("http://dl.bintray.com/gigamole/maven/")
            isAllowInsecureProtocol = true
        }
        maven {
            url = uri("https://jitpack.io")
        }

        gradlePluginPortal()

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://dl.bintray.com/gigamole/maven/")
            isAllowInsecureProtocol = true
        }
        maven {
            url = uri("https://jitpack.io")
        }
    }
}

rootProject.name = "test"
include(":app")
 