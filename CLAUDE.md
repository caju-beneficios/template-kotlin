# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Repository Purpose

This is a **Cookiecutter template repository** that generates Spring Boot 4 microservices in Kotlin with hexagonal architecture. It's not a runnable application but a template that creates production-ready microservices.

## Key Architecture

The template generates services using **Hexagonal Architecture (Ports & Adapters)** with:

- **Domain Layer**: Pure business logic with ports (interfaces) for external dependencies
- **Driver Layer**: Entry points (HTTP API, Kafka consumers)
- **Driven Layer**: External system adapters (JPA persistence, Kafka producers, REST clients, S3 storage)
- **Shared Domain**: Common exception hierarchy, pagination models, transaction management

Key patterns:
- **Sealed Exceptions**: AppException hierarchy with BusinessException, NotFoundException, etc.
- **Commands**: CQRS-style command objects for domain operations
- **Coroutines**: Suspend functions throughout for async operations
- **Events**: Domain events with Kafka publishing when enabled

## Template Variables

Core cookiecutter variables in `cookiecutter.json`:
- `resource_name`: Entity name (e.g., "Article") - drives all dynamic naming
- `include_kafka_events`: Toggle Kafka event publishing
- `include_pagination`: Toggle pagination support
- `project_name`: Generated project display name

Dynamic naming patterns:
- `{{cookiecutter.resource_name}}` → PascalCase (Article)
- `{{cookiecutter.resource_name_lower}}` → lowercase (article)
- `{{cookiecutter._resource_name_camel}}` → camelCase (article)

## Development Commands

**Primary development workflow** (using Makefile):
```bash
make set-all        # Initial setup: containers + database reset
make run           # Start the application (./gradlew clean run)
make test          # Run tests (./gradlew clean test)
make lint-format   # Auto-format code (spotlessApply)
```

**Database operations**:
```bash
make reset-db      # Drop, recreate, and seed database
make run-sql-seeds # Execute seed.sql
```

**Container management**:
```bash
make up            # Start Docker containers
make clean         # Stop and purge volumes/networks
make docker-restart # Force reset with fresh build
```

**Gradle commands**:
```bash
./gradlew clean build              # Full build
./gradlew check                    # All checks + coverage report
./gradlew bootJar                  # Generate app.jar
./gradlew spotlessApply           # Code formatting
```

## Technology Stack

- **Spring Boot 4.0.2** with Kotlin 2.2.21
- **Java 21 LTS** (managed via .sdkmanrc/mise.toml)
- **Gradle 8.14** with centralized version catalog
- **Jetty** web server (replaced Undertow for Servlet 6.1)
- **MySQL** with Flyway migrations
- **Kafka** with Springwolf documentation
- **JaCoCo 4.0.1** for code coverage
- **Kotest** for testing with SpringMockK
- **Embedded Kafka** for test environments (no TestContainers)

## Important Files

- `gradle/libs.versions.toml`: Centralized dependency versions
- `SPRING_BOOT_4_MIGRATION.md`: Detailed migration guide and troubleshooting
- `sonar-project.properties`: SonarQube integration configuration
- Template structure mirrors generated project structure under `{{cookiecutter.project_slug}}/`

## Template Formatting

**CRITICAL**: When modifying template files, maintain clean formatting to avoid extra empty lines in generated output. Many template files currently have formatting issues that create unwanted blank lines.

### Clean Formatting Pattern (GOOD)

Follow the pattern established in [Create{{cookiecutter._resource_name_camel}}Command.kt]({{cookiecutter.project_slug}}/domain/src/main/kotlin/br/com/caju/domain/{{cookiecutter.resource_name_lower}}/command/Create{{cookiecutter._resource_name_camel}}Command.kt):

```kotlin
data class Command({% if condition %}
    val field: Type,
    val other: String
{% else %}    // TODO: Add fields here
{% endif %})

fun Command.toModel(id: UUID) = Model(
    id = id,{% if condition %}
    field = field,
    other = other
{% else %}    // TODO: Map fields here
{% endif %})
```

### Key Principles

1. **Inline conditionals**: Start `{% if %}` on same line as code when possible
2. **No standalone conditionals**: Avoid conditionals on their own line before major declarations
3. **Consistent indentation**: Maintain proper indentation within conditional blocks
4. **Strip whitespace**: Use `{%- if -%}` and `{%- endif -%}` when wrapping large blocks

### Files Needing Cleanup

The following files have formatting issues that create extra empty lines:

**High Priority (create comment-only files or major spacing issues):**
- `{{cookiecutter.resource_name.capitalize()}}EventType.kt`
- `GetAll{{cookiecutter.resource_name.capitalize()}}sPaginatedUseCase.kt`
- `{{cookiecutter.resource_name.capitalize()}}EventProducerPort.kt`
- `GetAll{{cookiecutter.resource_name.capitalize()}}sPaginatedPort.kt`

**Medium Priority (multiple conditional imports/methods):**
- `{{cookiecutter.resource_name.capitalize()}}DataAccessPort.kt`
- `Create{{cookiecutter.resource_name.capitalize()}}UseCase.kt`
- `Update{{cookiecutter.resource_name.capitalize()}}UseCase.kt`
- `{{cookiecutter._resource_name_camel}}Controller.kt`

### Anti-Patterns to Avoid

```kotlin
// BAD: Creates empty lines
{% if condition %}
class MyClass {
    ...
}
{% else %}
// Comment only
{% endif %}

// BAD: Conditional on separate line
data class Command(
{% if condition %}
    val field: Type
{% endif %}
)

// GOOD: Inline conditional
data class Command({% if condition %}
    val field: Type
{% else %}    // TODO: Add fields
{% endif %})
```

## Testing Template

To test template generation:
```bash
cookiecutter . --no-input  # Use defaults
# or
cookiecutter . --no-input resource_name=Product include_kafka_events=n
```

Generated projects will have their own gradle build system and can be developed independently.