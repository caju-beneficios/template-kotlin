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
    implementation(projects.domain)
    implementation(projects.postgresqlPersistence)

    implementation(libs.spring.kafka)
    implementation(libs.spring.boot.starter.aspectj)

    //    implementation(libs.springwolf.kafka)
    //    implementation(libs.springwolf.ui)

    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.spring.kafka)

    testImplementation(libs.spring.boot.starter.web)
    testImplementation(libs.spring.boot.testcontainers)
    testImplementation(libs.spring.kafka.test)
    testImplementation(libs.wiremock.standalone)
    testImplementation(libs.testcontainers.junit.jupiter)
    testImplementation(testFixtures(projects.domain))
}
