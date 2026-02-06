buildscript {
    dependencies {
        classpath("org.flywaydb:flyway-database-postgresql:12.0.0")
        classpath("org.postgresql:postgresql:42.7.4")
    }
}

plugins { alias(libs.plugins.flyway) }

flyway {
    url = System.getenv("FLYWAY_URL")
        ?: "jdbc:postgresql://${System.getenv("APP_DB_HOST") ?: "localhost"}:${System.getenv("DB_PORT") ?: "5432"}/${System.getenv("APP_DB_NAME") ?: "{{cookiecutter.project_slug.replace('-', '_')}}"}"
    user = System.getenv("FLYWAY_USER") ?: System.getenv("APP_DB_USER") ?: "{{cookiecutter.project_slug.replace('-', '_')}}_user"
    password = System.getenv("FLYWAY_PASSWORD") ?: System.getenv("APP_DB_PASSWORD") ?: "{{cookiecutter.project_slug.replace('-', '_')}}_password"
    driver = "org.postgresql.Driver"
    locations = arrayOf("filesystem:src/main/resources/db/migration")
    createSchemas = true
    defaultSchema = System.getenv("DB_SCHEMA") ?: "public"
}

dependencies {
    implementation(projects.domain)
    testImplementation(projects.domain)
    testImplementation(testFixtures(projects.domain))

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

    implementation(libs.flyway.core)
    implementation(libs.flyway.postgresql)
    implementation(libs.postgresql.connector)

    testImplementation(libs.testcontainers.postgresql)
    testImplementation(libs.kotest.extensions.now)
}