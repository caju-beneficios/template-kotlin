package br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model

import br.com.caju.domain.shared.log.MdcVar

enum class {{cookiecutter.resource_name.capitalize()}}LogVar : MdcVar {
    {{cookiecutter.resource_name.upper()}}_ID,
    // TODO: Add additional MDC logging variables for your resource
    // Examples: {{cookiecutter.resource_name.upper()}}_NAME, {{cookiecutter.resource_name.upper()}}_EMAIL, etc.
}