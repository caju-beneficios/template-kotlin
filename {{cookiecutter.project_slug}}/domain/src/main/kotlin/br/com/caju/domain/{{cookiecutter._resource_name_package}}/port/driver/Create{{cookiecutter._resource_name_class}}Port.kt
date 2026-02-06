package br.com.caju.domain.{{cookiecutter._resource_name_package}}.port.driver

import br.com.caju.domain.{{cookiecutter._resource_name_package}}.command.Create{{cookiecutter._resource_name_class}}Command
import br.com.caju.domain.{{cookiecutter._resource_name_package}}.model.{{cookiecutter._resource_name_class}}

fun interface Create{{cookiecutter._resource_name_class}}Port {
    suspend operator fun invoke(command: Create{{cookiecutter._resource_name_class}}Command): {{cookiecutter._resource_name_class}}
}