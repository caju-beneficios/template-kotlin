package br.com.caju.domain.{{cookiecutter._resource_name_package}}.model

import java.util.UUID
import java.time.LocalDateTime

data class {{cookiecutter._resource_name_class}}(
    val id: UUID,{% if cookiecutter.resource_name == 'Article' %}
    val title: String,
    val content: String,
    val status: String,
{% else %}    // TODO: Add your domain-specific fields here
    // Examples: val name: String, val email: String, etc.
{% endif %}    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)