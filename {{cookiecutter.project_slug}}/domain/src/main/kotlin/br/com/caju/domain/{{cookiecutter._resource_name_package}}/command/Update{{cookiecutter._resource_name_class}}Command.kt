package br.com.caju.domain.{{cookiecutter._resource_name_package}}.command

import br.com.caju.domain.{{cookiecutter._resource_name_package}}.model.{{cookiecutter._resource_name_class}}
import java.util.UUID

data class Update{{cookiecutter._resource_name_class}}Command(
    val id: UUID,{% if cookiecutter.resource_name == 'Article' %}
    val title: String,
    val content: String,
    val status: String
{% else %}    // TODO: Add your domain-specific fields here
    // Examples: val name: String, val email: String, etc.
{% endif %})

fun Update{{cookiecutter._resource_name_class}}Command.toModel() = {{cookiecutter._resource_name_class}}(
    id = id,{% if cookiecutter.resource_name == 'Article' %}
    title = title,
    content = content,
    status = status,
{% else %}    // TODO: Map your command fields to model fields here
{% endif %}    createdAt = null, // Will be set by persistence layer
    updatedAt = null  // Will be set by persistence layer
)