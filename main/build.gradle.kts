plugins {
    alias(libs.plugins.spring.boot)
    application
}


tasks {
    test { jvmArgs("--add-opens", "java.base/java.lang.reflect=ALL-UNNAMED", "-Xmx2048m") }
    bootJar  {
        archiveFileName.set("app.${archiveExtension.get()}")
    }
}

application {
    mainClass.set("br.com.caju.ApplicationKt")

    applicationDefaultJvmArgs = listOf("-server", "-XX:+UseNUMA", "-XX:+UseParallelGC")
}



dependencies {
    rootProject.subprojects.filter { it != project }.forEach {
        implementation(it)
        testFixturesImplementation(it)

        it.takeIf { it != projects.domain }?.let { proj ->
            testImplementation(testFixtures(proj))
            testFixturesImplementation(testFixtures(proj))
        }
    }


    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.kafka:spring-kafka")

    runtimeOnly("org.flywaydb:flyway-core")
    runtimeOnly("org.flywaydb:flyway-mysql")

    testImplementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation(libs.wiremock.standalone)
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:kafka")
    testImplementation("org.testcontainers:mysql")
    testImplementation("org.testcontainers:jdbc")

    testRuntimeOnly("org.flywaydb:flyway-core")
}
