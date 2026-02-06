{% if cookiecutter.include_kafka_events == 'y' %}
package br.com.caju.{{cookiecutter.resource_name_lower}}.dto

import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model.{{cookiecutter.resource_name_class}}
import java.util.UUID

data class {{cookiecutter.resource_name_class}}MessageListenerDTO(
    val id: UUID,
{% if cookiecutter.resource_name == 'Article' %}
    val title: String,
    val content: String,
    val status: String,
{% else %}
    // TODO: Add your resource-specific fields here
{% endif %}
    val type: {{cookiecutter.resource_name_class}}EventListenerTypeDTO,
) {
    fun toModel() = {{cookiecutter.resource_name_class}}(
        id = id,
{% if cookiecutter.resource_name == 'Article' %}
        title = title,
        content = content,
        status = status
{% else %}
        // TODO: Map your DTO fields to domain model here
{% endif %}
    )
}

enum class {{cookiecutter.resource_name_class}}EventListenerTypeDTO {
    CREATE,
    UPDATE,
}
{% else %}
// Kafka events are disabled for this resource
{% endif %}