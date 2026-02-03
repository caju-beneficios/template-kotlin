package br.com.caju.domain.{{cookiecutter.resource_name.lower()}}.port.driver

import br.com.caju.domain.{{cookiecutter.resource_name.lower()}}.model.{{cookiecutter.resource_name.capitalize()}}
import java.util.UUID

fun interface Get{{cookiecutter.resource_name.capitalize()}}ByIdPort {
    suspend fun getById(id: UUID): {{cookiecutter.resource_name.capitalize()}}
}