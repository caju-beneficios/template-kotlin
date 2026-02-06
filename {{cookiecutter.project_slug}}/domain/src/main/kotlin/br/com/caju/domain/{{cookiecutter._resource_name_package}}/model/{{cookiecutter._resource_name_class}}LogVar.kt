package br.com.caju.domain.{{cookiecutter._resource_name_package}}.model

import br.com.caju.domain.shared.log.MdcVar

enum class {{cookiecutter._resource_name_class}}LogVar : MdcVar {
    {{cookiecutter._resource_name_constant}}_ID,
    // TODO: Add additional MDC logging variables for your resource
    // Examples: {{cookiecutter._resource_name_constant}}_NAME, {{cookiecutter._resource_name_constant}}_EMAIL, etc.
}