package br.com.caju.domain.{{cookiecutter.resource_name.lower()}}.port.driven

import br.com.caju.domain.{{cookiecutter.resource_name.lower()}}.model.{{cookiecutter.resource_name.capitalize()}}{% if cookiecutter.include_pagination == 'y' %}
import br.com.caju.domain.shared.pagination.model.PaginatedModel
import br.com.caju.domain.shared.pagination.model.Pagination{% endif %}
import java.util.UUID

interface {{cookiecutter.resource_name.capitalize()}}DataAccessPort {
    suspend fun save({{cookiecutter.resource_name.lower()}}: {{cookiecutter.resource_name.capitalize()}}): {{cookiecutter.resource_name.capitalize()}}

    suspend fun update({{cookiecutter.resource_name.lower()}}: {{cookiecutter.resource_name.capitalize()}}): {{cookiecutter.resource_name.capitalize()}}{% if cookiecutter.include_pagination == 'y' %}

    suspend fun findAll(pagination: Pagination): PaginatedModel<{{cookiecutter.resource_name.capitalize()}}>{% endif %}

    suspend fun findById(id: UUID): {{cookiecutter.resource_name.capitalize()}}?{% if cookiecutter.resource_name == 'Article' %}

    suspend fun findByTitle(title: String): {{cookiecutter.resource_name.capitalize()}}?

    suspend fun existsByTitle(title: String): Boolean{% else %}

    // TODO: Add your resource-specific finder methods here
    // Examples: findByName, findByEmail, existsByName, etc.{% endif %}
}