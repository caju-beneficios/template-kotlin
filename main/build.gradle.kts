plugins {
    alias(libs.plugins.spring.boot)
    application
}

tasks {
    test { jvmArgs("--add-opens", "java.base/java.lang.reflect=ALL-UNNAMED", "-Xmx2048m") }
    bootJar { archiveFileName.set("app.${archiveExtension.get()}") }
}

application {
    mainClass.set("br.com.caju.ApplicationKt")

    applicationDefaultJvmArgs = listOf("-server", "-XX:+UseNUMA", "-XX:+UseParallelGC")
}

dependencies {
    rootProject.subprojects
        .filter { it != project }
        .forEach {
            implementation(it)
            testFixturesImplementation(it)

            it.takeIf { it != projects.domain }
                ?.let { proj ->
                    testImplementation(testFixtures(proj))
                    testFixturesImplementation(testFixtures(proj))
                }
        }

    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.cloud.starter.openfeign)
    implementation(libs.spring.kafka)

    runtimeOnly(libs.flyway.core)
    runtimeOnly(libs.flyway.mysql)

    testImplementation(libs.spring.boot.starter.web)
    testImplementation(libs.spring.boot.starter.data.jpa)
    testImplementation(libs.spring.boot.testcontainers)
    testImplementation(libs.spring.kafka.test)
    testImplementation(libs.wiremock.standalone)
    testImplementation(libs.testcontainers.junit.jupiter)
    testImplementation(libs.testcontainers.mysql)
    testImplementation(libs.testcontainers.jdbc)

    testRuntimeOnly(libs.flyway.core)
}
