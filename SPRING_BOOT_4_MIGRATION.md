# Spring Boot 4 and JaCoCo 4 Migration Guide

This document describes all changes made to the template for Spring Boot 4.0.x and JaCoCo 4 compatibility.

## Overview

This migration includes:
- Upgrade to Spring Boot 4.0.2
- Upgrade to JaCoCo 4.0.1
- Upgrade to Gradle 8.14
- Downgrade from Java 25 to Java 21 LTS
- Spring Boot 4 dependency updates

## Required Changes

### 1. Gradle Version

**Upgraded:** 8.10.2 → 8.14

**Why:** Spring Boot 4.0.2 requires Gradle 8.14 or later

**File:** `gradle/wrapper/gradle-wrapper.properties`
```properties
distributionUrl=https\://services.gradle.org/distributions/gradle-8.14-bin.zip
```

**Reference:** [Spring Boot 4.0 Migration Guide](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.0-Migration-Guide)

---

### 2. Java Version

**Changed:** Java 25 → Java 21 LTS

**Why:** Better plugin compatibility and Java 21 is an LTS version

**Files Updated:**
- `gradle/libs.versions.toml`: `java = "21"`
- `mise.toml`: `java = "21.0.2"`
- `.sdkmanrc`: `java=21.0.2-tem`

---

### 3. JaCoCo Configuration

**Version:** 4.0.1 (gradle-jacoco-log plugin)

**Plugin Added:** `jacoco-report-aggregation`

**Changes in `build.gradle.kts`:**

```kotlin
plugins {
    // ... other plugins
    alias(libs.plugins.jacoco)
    `jacoco-report-aggregation`  // NEW: Added for coverage aggregation
}
```

**Removed:**
- Manual `reporting` block with `TestSuiteType`
- Unused imports (`JacocoReport`, `TestSuiteType`)
- Manual report configuration

**Simplified tasks block:**
```kotlin
tasks {
    check {
        dependsOn(named("testCodeCoverageReport"))  // Auto-created by jacoco-report-aggregation
    }
}
```

**Reference:** [gradle-jacoco-log documentation](https://github.com/barfuin/gradle-jacoco-log)

---

### 4. Spring Boot 4 Dependency Changes

#### 4.1. AOP Dependency Renamed

**Old:** `spring-boot-starter-aop`
**New:** `spring-boot-starter-aspectj`

**Why:** In Spring Boot 4.0, the AOP starter was renamed to clarify its scope

**File:** `gradle/libs.versions.toml`
```toml
spring-boot-starter-aspectj = { version.ref = "spring-boot", module = "org.springframework.boot:spring-boot-starter-aspectj" }
```

**Build files updated:**
- `driver/http-api/build.gradle.kts`: `libs.spring.boot.starter.aspectj`
- `driver/kafka-consumer/build.gradle.kts`: `libs.spring.boot.starter.aspectj`

**Reference:** [Spring Boot 4.0.0 Release](https://spring.io/blog/2025/11/20/spring-boot-4-0-0-available-now/)

#### 4.2. Undertow Removed

**Old:** `spring-boot-starter-undertow`
**New:** `spring-boot-starter-jetty` (or Tomcat)

**Why:** Undertow was dropped in Spring Boot 4.0 as it doesn't support Servlet 6.1 baseline

**File:** `gradle/libs.versions.toml`
```toml
spring-boot-starter-jetty = { version.ref = "spring-boot", module = "org.springframework.boot:spring-boot-starter-jetty" }
```

**Build files updated:**
- `driver/http-api/build.gradle.kts`: `libs.spring.boot.starter.jetty`

**Note:** You can also use the default Tomcat (no explicit dependency needed) or switch to Jetty as shown above.

**Reference:** [Spring Boot 4.0 Migration Guide - Undertow](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.0-Migration-Guide)

---

### 5. Version Catalog Explicit Versions

All Spring Boot dependencies now include explicit `version.ref`:

**Before:**
```toml
spring-boot-starter-web = { module = "org.springframework.boot:spring-boot-starter-web" }
```

**After:**
```toml
spring-boot-starter-web = { version.ref = "spring-boot", module = "org.springframework.boot:spring-boot-starter-web" }
```

**Why:** Ensures proper version resolution with Gradle 8.14+

**Dependencies updated:**
- All `spring-boot-starter-*` dependencies
- `spring-kafka` and `spring-kafka-test`
- `spring-tx`
- `spring-cloud-starter-openfeign` (uses `spring-cloud` version)

---

### 6. Deprecation Fixes

**Fixed:** Spotless `baseDir` deprecation

**Before:**
```kotlin
withType<com.diffplug.gradle.spotless.SpotlessTask> {
    reporting { baseDir = linterReportingDirectory.asFile }
}
```

**After:**
```kotlin
withType<com.diffplug.gradle.spotless.SpotlessTask> {
    reporting { baseDirectory.set(linterReportingDirectory) }
}
```

---

### 7. Repository Configuration

**Removed:** Duplicate `repositories` block from `allprojects`

**Why:** `settings.gradle.kts` already has `dependencyResolutionManagement` configured

The repositories are now centrally managed in `settings.gradle.kts`:
```kotlin
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        mavenLocal()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://packages.confluent.io/maven/") }
        maven { url = uri("https://repo.spring.io/milestone") }
    }
}
```

---

## Migration Checklist

When generating a new project from this template, verify:

- [ ] Gradle wrapper is version 8.14
- [ ] Java toolchain is set to 21
- [ ] JaCoCo plugin is version 4.0.1
- [ ] Spring Boot version is 4.0.2
- [ ] No references to `spring-boot-starter-aop` (renamed to `aspectj`)
- [ ] No references to `spring-boot-starter-undertow` (replaced with Jetty or removed)
- [ ] All Spring Boot dependencies have explicit versions in version catalog
- [ ] Build succeeds without errors
- [ ] Tests run successfully
- [ ] JaCoCo coverage report generates correctly

---

## Troubleshooting

### Build fails with "requires Gradle 8.x (8.14 or later)"
- Ensure `gradle/wrapper/gradle-wrapper.properties` has Gradle 8.14
- Run `./gradlew --stop` to stop old daemons
- Run `./gradlew wrapper --gradle-version 8.14` to update wrapper

### Dependencies not found
- Ensure `settings.gradle.kts` has `dependencyResolutionManagement` configured
- Remove any `repositories` blocks from `build.gradle.kts`
- Verify all dependencies in version catalog have explicit versions

### JaCoCo reports not generating
- Ensure `jacoco-report-aggregation` plugin is applied in root `build.gradle.kts`
- Check that `gradle-jacoco-log` version is 4.0.1
- Run `./gradlew clean test` to regenerate reports

---

## Additional Resources

- [Spring Boot 4.0.0 Release Notes](https://spring.io/blog/2025/11/20/spring-boot-4-0-0-available-now/)
- [Spring Boot 4.0 Migration Guide](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.0-Migration-Guide)
- [Gradle 8.14 Release Notes](https://docs.gradle.org/8.14/release-notes.html)
- [gradle-jacoco-log Plugin](https://github.com/barfuin/gradle-jacoco-log)
- [Gradle JaCoCo Report Aggregation Plugin](https://docs.gradle.org/current/userguide/jacoco_report_aggregation_plugin.html)

---

**Last Updated:** 2026-02-02
**Template Version:** 0.1.0