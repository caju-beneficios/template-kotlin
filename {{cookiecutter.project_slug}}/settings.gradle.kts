rootProject.name = "{{ cookiecutter.project_slug }}"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include("domain")
project(":domain").projectDir = file("domain")

// DRIVER
include("http-api", "kafka-consumer")

project(":http-api").projectDir = file("driver/http-api")
project(":kafka-consumer").projectDir = file("driver/kafka-consumer")

// DRIVEN
include("postgresql-persistence")

project(":postgresql-persistence").projectDir = file("driven/postgresql-persistence")


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
