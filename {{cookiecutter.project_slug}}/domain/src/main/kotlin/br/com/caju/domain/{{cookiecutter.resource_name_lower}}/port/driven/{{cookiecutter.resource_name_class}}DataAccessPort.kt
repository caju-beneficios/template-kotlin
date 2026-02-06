package br.com.caju.domain.{{cookiecutter.resource_name_lower}}.port.driven

import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model.{{cookiecutter.resource_name_class}}
{% if cookiecutter.include_pagination == 'y' %}
import br.com.caju.domain.shared.pagination.model.PaginatedModel
import br.com.caju.domain.shared.pagination.model.Pagination
{% endif %}
import java.util.UUID

interface {{cookiecutter.resource_name_class}}DataAccessPort {
    suspend fun save({{cookiecutter.resource_name_lower}}: {{cookiecutter.resource_name_class}}): {{cookiecutter.resource_name_class}}

    suspend fun update({{cookiecutter.resource_name_lower}}: {{cookiecutter.resource_name_class}}): {{cookiecutter.resource_name_class}}
    {% if cookiecutter.include_pagination == 'y' %}

    suspend fun findAll(pagination: Pagination): PaginatedModel<{{cookiecutter.resource_name_class}}>
    {% endif %}

    suspend fun findById(id: UUID): {{cookiecutter.resource_name_class}}?

    {% if cookiecutter.resource_name == 'Article' %}
    suspend fun findByTitle(title: String): {{cookiecutter.resource_name_class}}?

    suspend fun existsByTitle(title: String): Boolean
    {% else %}

    // TODO: Add your resource-specific finder methods here
    // Examples: findByName, findByEmail, existsByName, etc.{% endif %}
}