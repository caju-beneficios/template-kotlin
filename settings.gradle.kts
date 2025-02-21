rootProject.name = "quickstart-kotlin-spring"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include("main", "domain")

// DRIVER
include("rest-server", "event-consumer")

project(":rest-server").projectDir = file("driver/rest-server")

project(":event-consumer").projectDir = file("driver/event-consumer")

// DRIVEN
include("persistence", "storage", "event-producer", "rest-client")

project(":persistence").projectDir = file("driven/persistence")

project(":storage").projectDir = file("driven/storage")

project(":event-producer").projectDir = file("driven/event-producer")

project(":rest-client").projectDir = file("driven/rest-client")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        mavenLocal()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://packages.confluent.io/maven/") }
        maven { url = uri("https://repo.spring.io/milestone") }
    }
}

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://packages.confluent.io/maven/") }
        maven { url = uri("https://repo.spring.io/milestone") }
    }
}
