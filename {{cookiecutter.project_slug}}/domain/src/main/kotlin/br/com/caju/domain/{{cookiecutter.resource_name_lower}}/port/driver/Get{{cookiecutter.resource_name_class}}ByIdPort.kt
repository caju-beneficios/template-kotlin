package br.com.caju.domain.{{cookiecutter.resource_name_lower}}.port.driver

import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model.{{cookiecutter.resource_name_class}}
import java.util.UUID

fun interface Get{{cookiecutter.resource_name_class}}ByIdPort {
    suspend fun getById(id: UUID): {{cookiecutter.resource_name_class}}
}