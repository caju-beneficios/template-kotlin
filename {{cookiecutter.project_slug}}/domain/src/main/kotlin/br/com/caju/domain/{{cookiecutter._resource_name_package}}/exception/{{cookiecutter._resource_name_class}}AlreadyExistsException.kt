package br.com.caju.domain.{{cookiecutter._resource_name_package}}.exception

import br.com.caju.domain.shared.exception.AppException

// TODO: Customize this exception for your resource
data class {{cookiecutter._resource_name_class}}AlreadyExistsException(val identifier: String) :
    AppException.AlreadyExistsException(
        message = MESSAGE,
        errorKey = ERROR_KEY,
        data = mapOf("identifier" to identifier),
        expected = true,
    ) {

    companion object {
        const val MESSAGE = "{{cookiecutter._resource_name_class}} already exists"
        const val ERROR_KEY = "{{cookiecutter._resource_name_constant}}_ALREADY_EXISTS"
    }
}