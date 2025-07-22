pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "TodayIsAnnualLeave"
include(":app")

include(":data")
include(":di")

include(":core:designsystem")
include(":core:network")
include(":core:db")
include(":core:model")
include(":core:ui")
