package br.com.caju.kafkaproducer.{{cookiecutter.resource_name_lower}}.dto{% if cookiecutter.include_kafka_events == 'y' %}

import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model.{{cookiecutter.resource_name_camel}}
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model.{{cookiecutter.resource_name.capitalize()}}EventType
import java.util.UUID

data class {{cookiecutter.resource_name_camel}}MessageDTO(
    val id: UUID,{% if cookiecutter.resource_name == 'Article' %}
    val title: String,
    val content: String,
    val status: String,
{% else %}    // TODO: Add your resource-specific fields here
    // Examples: val name: String, val email: String, etc.
{% endif %}    val type: {{cookiecutter.resource_name_camel}}EventTypeDTO,
)

enum class {{cookiecutter.resource_name_camel}}EventTypeDTO {
    CREATED,
    UPDATED,
}

fun {{cookiecutter.resource_name.capitalize()}}EventType.toDTO() = {{cookiecutter.resource_name_camel}}EventTypeDTO.valueOf(name)

fun {{cookiecutter.resource_name_camel}}.toEventMessage({{cookiecutter.resource_name_lower}}EventType: {{cookiecutter.resource_name.capitalize()}}EventType) =
    {{cookiecutter.resource_name_camel}}MessageDTO({% if cookiecutter.resource_name == 'Article' %}
        id = id,
        title = title,
        content = content,
        status = status,
        type = {{cookiecutter.resource_name_lower}}EventType.toDTO()
{% else %}        // TODO: Map your resource fields here
        id = id,
        // name = name,
        // email = email,
        type = {{cookiecutter.resource_name_lower}}EventType.toDTO()
{% endif %}    ){% else %}

// Kafka events are disabled for this resource{% endif %}