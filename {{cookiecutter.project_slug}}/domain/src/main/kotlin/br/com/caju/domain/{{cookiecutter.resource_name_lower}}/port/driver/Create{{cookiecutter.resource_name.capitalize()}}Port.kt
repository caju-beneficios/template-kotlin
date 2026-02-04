package br.com.caju.domain.{{cookiecutter.resource_name_lower}}.port.driver

import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.command.Create{{cookiecutter.resource_name.capitalize()}}Command
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model.{{cookiecutter.resource_name.capitalize()}}

fun interface Create{{cookiecutter.resource_name.capitalize()}}Port {
    suspend operator fun invoke(command: Create{{cookiecutter.resource_name.capitalize()}}Command): {{cookiecutter.resource_name.capitalize()}}
}