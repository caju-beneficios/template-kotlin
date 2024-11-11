dependencies {
    implementation(projects.domain)
    testImplementation(projects.domain)

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    runtimeOnly("com.mysql:mysql-connector-j")

    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa")
    testImplementation("org.springframework.boot:spring-boot-autoconfigure")

    testRuntimeOnly("org.flywaydb:flyway-core")
    testRuntimeOnly("org.flywaydb:flyway-mysql")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:mysql")

    implementation("jakarta.persistence:jakarta.persistence-api")

    runtimeOnly("org.flywaydb:flyway-mysql")

    testImplementation(libs.testcontainers.mysql)
    testImplementation(libs.kotest.extensions.now)

}
