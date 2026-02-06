package br.com.caju.domain.{{cookiecutter._resource_name_package}}.port.driver

import br.com.caju.domain.{{cookiecutter._resource_name_package}}.model.{{cookiecutter._resource_name_class}}
import java.util.UUID

fun interface Get{{cookiecutter._resource_name_class}}ByIdPort {
    suspend fun getById(id: UUID): {{cookiecutter._resource_name_class}}
}