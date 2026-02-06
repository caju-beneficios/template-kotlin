package br.com.caju.domain.{{cookiecutter._resource_name_package}}.port.driver{% if cookiecutter.include_pagination == 'y' %}

import br.com.caju.domain.{{cookiecutter._resource_name_package}}.model.{{cookiecutter._resource_name_class}}
import br.com.caju.domain.shared.pagination.model.PaginatedModel
import br.com.caju.domain.shared.pagination.model.Pagination

fun interface GetAll{{cookiecutter._resource_name_class}}sPaginatedPort {
    suspend fun getAll(pagination: Pagination): PaginatedModel<{{cookiecutter._resource_name_class}}>
}{% else %}

// Pagination is disabled for this resource{% endif %}