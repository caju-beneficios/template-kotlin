package br.com.caju.domain.{{cookiecutter.resource_name_lower}}.port.driver{% if cookiecutter.include_pagination == 'y' %}

import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model.{{cookiecutter.resource_name.capitalize()}}
import br.com.caju.domain.shared.pagination.model.PaginatedModel
import br.com.caju.domain.shared.pagination.model.Pagination

fun interface GetAll{{cookiecutter.resource_name.capitalize()}}sPaginatedPort {
    suspend fun getAll(pagination: Pagination): PaginatedModel<{{cookiecutter.resource_name.capitalize()}}>
}{% else %}

// Pagination is disabled for this resource{% endif %}