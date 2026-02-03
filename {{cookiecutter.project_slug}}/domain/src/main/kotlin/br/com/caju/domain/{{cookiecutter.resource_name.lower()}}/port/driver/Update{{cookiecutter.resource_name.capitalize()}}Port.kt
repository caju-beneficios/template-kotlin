package br.com.caju.domain.{{cookiecutter.resource_name.lower()}}.port.driver

import br.com.caju.domain.{{cookiecutter.resource_name.lower()}}.command.Update{{cookiecutter.resource_name.capitalize()}}Command
import br.com.caju.domain.{{cookiecutter.resource_name.lower()}}.model.{{cookiecutter.resource_name.capitalize()}}

fun interface Update{{cookiecutter.resource_name.capitalize()}}Port {
    suspend operator fun invoke(command: Update{{cookiecutter.resource_name.capitalize()}}Command): {{cookiecutter.resource_name.capitalize()}}
}