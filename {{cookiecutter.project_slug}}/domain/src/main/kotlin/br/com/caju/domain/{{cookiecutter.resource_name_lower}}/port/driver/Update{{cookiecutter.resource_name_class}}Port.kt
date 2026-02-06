package br.com.caju.domain.{{cookiecutter.resource_name_lower}}.port.driver

import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.command.Update{{cookiecutter.resource_name_class}}Command
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model.{{cookiecutter.resource_name_class}}

fun interface Update{{cookiecutter.resource_name_class}}Port {
    suspend operator fun invoke(command: Update{{cookiecutter.resource_name_class}}Command): {{cookiecutter.resource_name_class}}
}