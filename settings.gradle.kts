pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        id("com.google.gms.google-services") version "4.4.0"
        id("androidx.navigation.safeargs.kotlin") version "2.7.0" // <<< נדרש!
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "BiteApp"
include(":app")
