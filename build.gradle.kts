import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.gradle.api.tasks.testing.logging.TestLogEvent.STARTED

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.jacoco)
    alias(libs.plugins.spotless)
    alias(libs.plugins.sonarqube)
    `java-test-fixtures`
    jacoco
    `version-catalog`
}

val patternsToExcludeFromTesting =
    listOf(
        "**/config/**",
        "**/dto/**",
        "**/model/**",
        "**/infra/**",
        "**/test/**",
        "**/testFixtures/**",
        "**/Application*",
        "**/shared/utils/**",
        "**/shared/Auditable*",
        "**/exception*/**",
    )

java { toolchain { languageVersion = JavaLanguageVersion.of(libs.versions.java.get()) } }

allprojects {
    val libs = rootProject.libs

    group = "br.com.caju"
    version = "0.0.1"

    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.allopen")
        plugin("org.jetbrains.kotlin.plugin.noarg")
        plugin("org.jetbrains.kotlin.plugin.jpa")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("jacoco")
        plugin("java-test-fixtures")
        plugin("com.diffplug.spotless")
        plugin("org.sonarqube")
        plugin("version-catalog")
    }

    spotless {
        kotlin { ktfmt().kotlinlangStyle() }
        kotlinGradle { ktfmt().kotlinlangStyle() }
        java { googleJavaFormat() }
    }

    configurations { compileOnly { extendsFrom(configurations.annotationProcessor.get()) } }

    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://packages.confluent.io/maven/") }
    }

    dependencies {
        implementation(platform(libs.spring.boot.dependencies))
        implementation(platform(libs.spring.boot.cloud.dependencies))

        testImplementation(platform("org.springframework.boot:spring-boot-dependencies:3.3.4"))
        testImplementation(platform(libs.spring.boot.cloud.dependencies))

        testFixturesImplementation(platform(libs.spring.boot.dependencies))
        testFixturesImplementation(platform(libs.spring.boot.cloud.dependencies))

        implementation("org.springframework:spring-tx")
        implementation("org.springframework.boot:spring-boot-autoconfigure")
        implementation("org.springframework.boot:spring-boot-starter-logging")
        implementation("org.springframework.boot:spring-boot-starter")

        implementation(kotlin("reflect"))
        implementation(kotlin("stdlib-jdk8"))
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.kotlinx.coroutines.reactor)
        implementation(libs.kotlinx.coroutines.reactive)

        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

        implementation(libs.logback.encoder)
        implementation(libs.dd.trace.api)

        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(module = "mockito-core")
        }
        testImplementation(libs.springmockk)
        testImplementation(libs.kotest.runner.junit5.jvm)
        testImplementation(libs.kotest.assertions.core.jvm)
        testImplementation(libs.kotest.property.jvm)
        testImplementation(libs.kotest.extensions.spring)

        testFixturesImplementation(libs.instancio.junit)
        testFixturesImplementation(libs.assertj.core)
        testFixturesImplementation(kotlin("reflect"))
        testFixturesImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(module = "mockito-core")
        }
        testFixturesImplementation(libs.springmockk)
        testFixturesImplementation(libs.kotest.runner.junit5.jvm)
        testFixturesImplementation(libs.kotest.property.jvm)

        runtimeOnly(libs.logback.encoder)

        testApi(testFixtures(rootProject.projects.domain))
        project.name
            .takeIf { it != rootProject.projects.domain.name }
            ?.let { testFixturesApi(testFixtures(rootProject.projects.domain)) }

        //		implementation("org.springframework.boot:spring-boot-starter-actuator")
        //		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        //		implementation("org.springframework.boot:spring-boot-starter-graphql")
        //		implementation("org.springframework.boot:spring-boot-starter-webflux")
        //		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        //		implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
        //		implementation("org.flywaydb:flyway-core")
        //		implementation("org.flywaydb:flyway-mysql")
        //		implementation("org.jetbrains.kotlin:kotlin-reflect")
        //		implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
        //
        //	implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-reactor-resilience4j")
        //		implementation("org.springframework.kafka:spring-kafka")
        //		developmentOnly("org.springframework.boot:spring-boot-docker-compose")
        //		runtimeOnly("com.mysql:mysql-connector-j")
        //		runtimeOnly("io.micrometer:micrometer-registry-datadog")
        //		annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
        //		testImplementation("org.springframework.boot:spring-boot-starter-test")
        //		testImplementation("org.springframework.boot:spring-boot-testcontainers")
        //		testImplementation("io.projectreactor:reactor-test")
        //		testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        //		testImplementation("org.springframework.graphql:spring-graphql-test")
        //		testImplementation("org.springframework.kafka:spring-kafka-test")
        //		testImplementation("org.testcontainers:junit-jupiter")
        //		testImplementation("org.testcontainers:kafka")
        //		testImplementation("org.testcontainers:mysql")
        //		testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    reporting { baseDirectory.set(reportingDirectory) }

    kotlin {
        compilerOptions { freeCompilerArgs.addAll("-Xjsr305=strict") }
        sourceSets {
            main { kotlin.setSrcDirs(listOf("src/main/kotlin")) }
            test { kotlin.setSrcDirs(listOf("src/test/kotlin")) }
        }
    }

    allOpen {
        annotation("jakarta.persistence.Entity")
        annotation("jakarta.persistence.MappedSuperclass")
        annotation("jakarta.persistence.Embeddable")
    }

    tasks {
        compileJava { options.encoding = "UTF-8" }

        withType<Test> {
            useJUnitPlatform()
            jvmArgs("--add-opens", "java.base/java.lang.reflect=ALL-UNNAMED")

            testLogging {
                showStandardStreams = true
                events =
                    setOf(
                        PASSED,
                        FAILED,
                        STARTED,
                        SKIPPED,
                    )
                exceptionFormat = FULL
            }

            reports {
                html.apply {
                    outputLocation.set(unitTestReportingDirectory.dir("html").asFile)
                    required.set(true)
                }

                junitXml.apply {
                    outputLocation.set(unitTestReportingDirectory.dir("xml").asFile)
                    required.set(true)
                    isOutputPerTestCase = false
                    mergeReruns.set(true)
                }
            }

            finalizedBy(jacocoTestReport, jacocoTestCoverageVerification)
        }

        withType<com.diffplug.gradle.spotless.SpotlessTask> {
            reporting { baseDir = linterReportingDirectory.asFile }
        }

        jacocoTestReport {
            classDirectories.setFrom(
                files(
                    classDirectories.files.map {
                        fileTree(it) { setExcludes(patternsToExcludeFromTesting) }
                    }
                )
            )
        }
    }

    sonar {
        properties {
            property("sonar.projectKey", "<project-key>")
            property("sonar.projectName", "<project-name>")
            property(
                "sonar.coverage.jacoco.xmlReportPaths",
                "/runner/_work/<project-repo-name>/<project-name>/build/report/test/xml/result.xml",
            )
            property("sonar.exclusions", patternsToExcludeFromTesting.joinToString(separator = ","))
        }
    }
}

tasks {
    build { setDependsOn(subprojects.map { it.getTasksByName("build", false) }) }

    check {
        setDependsOn(subprojects.map { it.getTasksByName("check", false) })
        finalizedBy(jacocoAggregatedReport)
    }

    jacocoAggregatedReport {
        reports {
            csv.apply {
                outputLocation.set(unitTestReportingDirectory.dir("csv").file("result.csv").asFile)
                required.set(false)
            }

            html.apply {
                outputLocation.set(unitTestReportingDirectory.dir("html").asFile)
                required.set(true)
            }

            xml.apply {
                outputLocation.set(unitTestReportingDirectory.dir("xml").file("result.xml").asFile)
                required.set(true)
            }
        }
    }
}

val Project.reportingDirectory: Directory
    get() = layout.buildDirectory.dir("report").get()

val Project.linterReportingDirectory: Directory
    get() = reportingDirectory.dir("linter")

val Project.unitTestReportingDirectory: Directory
    get() = reportingDirectory.dir("test")

val Project.coverageReportingDirectory: Directory
    get() = reporting.baseDirectory.get().dir("coverage")
