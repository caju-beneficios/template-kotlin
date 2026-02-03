dependencies {
    implementation(projects.domain)
    testImplementation(projects.domain)

    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.autoconfigure)

    testImplementation(libs.spring.boot.testcontainers)
    testImplementation(libs.spring.boot.starter.data.jpa)
    testImplementation(libs.spring.boot.autoconfigure)

    testRuntimeOnly(libs.flyway.core)
    testRuntimeOnly(libs.flyway.postgresql)

    testImplementation(libs.testcontainers.junit.jupiter)
    testImplementation(libs.testcontainers.postgresql)

    implementation(libs.jakarta.persistence.api)

    runtimeOnly(libs.flyway.postgresql)
    runtimeOnly(libs.postgresql.connector)

    testImplementation(libs.testcontainers.postgresql)
    testImplementation(libs.kotest.extensions.now)
}