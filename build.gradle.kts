import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.gradle.api.tasks.testing.logging.TestLogEvent.STARTED

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.all.open)
    alias(libs.plugins.kotlin.no.arg)
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.native.build.tools) apply false
    alias(libs.plugins.jacoco)
    alias(libs.plugins.spotless)
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
        plugin("version-catalog")
        plugin("com.google.devtools.ksp")
    }

    spotless {
        kotlin { ktfmt().kotlinlangStyle() }
        kotlinGradle { ktfmt().kotlinlangStyle() }
        java { googleJavaFormat() }
    }

    configurations { compileOnly { extendsFrom(configurations.annotationProcessor.get()) } }

    dependencies {
        implementation(platform(libs.spring.boot.dependencies))
        implementation(platform(libs.spring.boot.cloud.dependencies))

        annotationProcessor(libs.spring.boot.configuration.processor)
        testAnnotationProcessor(libs.spring.boot.configuration.processor)

        testImplementation(platform(libs.spring.boot.dependencies))
        testImplementation(platform(libs.spring.boot.cloud.dependencies))

        testFixturesImplementation(platform(libs.spring.boot.dependencies))
        testFixturesImplementation(platform(libs.spring.boot.cloud.dependencies))

        implementation(libs.spring.tx)
        implementation(libs.spring.boot.autoconfigure)
        implementation(libs.spring.boot.starter.logging)
        implementation(libs.spring.boot.starter)

        implementation(kotlin("reflect"))
        implementation(kotlin("stdlib-jdk8"))
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.kotlinx.coroutines.reactor)
        implementation(libs.kotlinx.coroutines.reactive)

        implementation(libs.jackson.kotlin)
        implementation(libs.jackson.jdk8)
        implementation(libs.jackson.jsr310)

        implementation(libs.logback.encoder)
        implementation(libs.dd.trace.api)

        testImplementation(libs.spring.boot.starter.test) { exclude(module = "mockito-core") }
        testImplementation(libs.springmockk)
        testImplementation(libs.kotest.runner.junit5.jvm)
        testImplementation(libs.kotest.assertions.core.jvm)
        testImplementation(libs.kotest.property.jvm)
        testImplementation(libs.kotest.extensions.spring)

        testFixturesImplementation(libs.assertj.core)
        testFixturesImplementation(kotlin("reflect"))
        testFixturesImplementation(libs.spring.boot.starter.test) {
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
                events = setOf(PASSED, FAILED, STARTED, SKIPPED)
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
                outputLocation.set(coverageReportingDirectory.dir("csv").file("result.csv").asFile)
                required.set(false)
            }

            html.apply {
                outputLocation.set(coverageReportingDirectory.dir("html").asFile)
                required.set(true)
            }

            xml.apply {
                outputLocation.set(coverageReportingDirectory.dir("xml").file("result.xml").asFile)
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
    get() = reportingDirectory.dir("coverage")
