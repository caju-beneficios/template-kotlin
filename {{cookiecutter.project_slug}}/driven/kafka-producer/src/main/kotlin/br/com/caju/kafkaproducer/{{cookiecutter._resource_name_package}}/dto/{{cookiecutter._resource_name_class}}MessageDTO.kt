package br.com.caju.kafkaproducer.{{cookiecutter._resource_name_package}}.dto{% if cookiecutter.include_kafka_events == 'y' %}

import br.com.caju.domain.{{cookiecutter._resource_name_package}}.model.{{cookiecutter._resource_name_class}}
import br.com.caju.domain.{{cookiecutter._resource_name_package}}.model.{{cookiecutter._resource_name_class}}EventType
import java.util.UUID

data class {{cookiecutter._resource_name_class}}MessageDTO(
    val id: UUID,{% if cookiecutter.resource_name == 'Article' %}
    val title: String,
    val content: String,
    val status: String,
{% else %}    // TODO: Add your resource-specific fields here
    // Examples: val name: String, val email: String, etc.
{% endif %}    val type: {{cookiecutter._resource_name_class}}EventTypeDTO,
)

enum class {{cookiecutter._resource_name_class}}EventTypeDTO {
    CREATED,
    UPDATED,
}

fun {{cookiecutter._resource_name_class}}EventType.toDTO() = {{cookiecutter._resource_name_class}}EventTypeDTO.valueOf(name)

fun {{cookiecutter._resource_name_class}}.toEventMessage({{cookiecutter._resource_name_package}}EventType: {{cookiecutter._resource_name_class}}EventType) =
    {{cookiecutter._resource_name_class}}MessageDTO({% if cookiecutter.resource_name == 'Article' %}
        id = id,
        title = title,
        content = content,
        status = status,
        type = {{cookiecutter._resource_name_package}}EventType.toDTO()
{% else %}        // TODO: Map your resource fields here
        id = id,
        // name = name,
        // email = email,
        type = {{cookiecutter._resource_name_package}}EventType.toDTO()
{% endif %}    ){% else %}

// Kafka events are disabled for this resource{% endif %}