package br.com.caju.{{cookiecutter.resource_name_lower}}.dto

import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.command.Create{{cookiecutter.resource_name_class}}Command
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.command.Update{{cookiecutter.resource_name_class}}Command
import java.util.UUID

data class {{cookiecutter.resource_name_class}}RequestDTO(
{% if cookiecutter.resource_name == 'Article' %}
    val title: String,
    val content: String,
    val status: String
{% else %}
    // TODO: Add your resource-specific fields here
    // Examples: val name: String, val email: String, etc.
{% endif %}
)

fun {{cookiecutter.resource_name_class}}RequestDTO.toCreateCommand() = Create{{cookiecutter.resource_name_class}}Command(
{% if cookiecutter.resource_name == 'Article' %}
    title = title,
    content = content,
    status = status
{% else %}
    // TODO: Map your DTO fields to command fields here
{% endif %}
)

fun {{cookiecutter.resource_name_class}}RequestDTO.toUpdateCommand(id: UUID) = Update{{cookiecutter.resource_name_class}}Command(
    id = id,
{% if cookiecutter.resource_name == 'Article' %}
    title = title,
    content = content,
    status = status
{% else %}
    // TODO: Map your DTO fields to command fields here
{% endif %}
)