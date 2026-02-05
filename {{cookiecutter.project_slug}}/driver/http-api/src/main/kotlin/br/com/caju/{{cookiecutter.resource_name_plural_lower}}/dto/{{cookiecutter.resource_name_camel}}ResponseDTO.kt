package br.com.caju.{{cookiecutter.resource_name_plural_lower}}.dto

import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model.{{cookiecutter.resource_name_camel}}{% if cookiecutter.include_pagination == 'y' %}
import br.com.caju.domain.shared.pagination.model.PaginatedModel
import br.com.caju.shared.dto.PaginatedResponseDTO
import br.com.caju.shared.dto.toResponseDTO as toPaginatedResponseDTO
import br.com.caju.shared.dto.map{% endif %}
import java.time.LocalDateTime
import java.util.UUID

data class {{cookiecutter.resource_name_camel}}ResponseDTO(
    val id: UUID,{% if cookiecutter.resource_name == 'Article' %}
    val title: String,
    val content: String,
    val status: String,
{% else %}    // TODO: Add your resource-specific fields here
    // Examples: val name: String, val email: String, etc.
{% endif %}    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)

fun {{cookiecutter.resource_name_camel}}.toDTO() = {{cookiecutter.resource_name_camel}}ResponseDTO(
    id = id,{% if cookiecutter.resource_name == 'Article' %}
    title = title,
    content = content,
    status = status,
{% else %}    // TODO: Map your model fields to DTO fields here
{% endif %}    createdAt = createdAt,
    updatedAt = updatedAt
){% if cookiecutter.include_pagination == 'y' %}

fun PaginatedModel<{{cookiecutter.resource_name_camel}}>.toResponseDTO(
    pagination: br.com.caju.domain.shared.pagination.model.Pagination
): PaginatedResponseDTO<{{cookiecutter.resource_name_camel}}ResponseDTO> =
    this.toPaginatedResponseDTO(pagination).map { it.toDTO() }{% endif %}