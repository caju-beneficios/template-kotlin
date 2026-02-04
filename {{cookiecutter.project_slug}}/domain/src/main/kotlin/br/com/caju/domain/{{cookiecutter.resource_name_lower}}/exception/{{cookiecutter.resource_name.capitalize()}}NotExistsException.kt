package br.com.caju.domain.{{cookiecutter.resource_name_lower}}.exception

import br.com.caju.domain.shared.exception.AppException
import java.util.UUID

data class {{cookiecutter.resource_name.capitalize()}}NotExistsException(val id: UUID) :
    AppException.NotFoundException(
        message = MESSAGE,
        errorKey = ERROR_KEY,
        data = mapOf("id" to id),
        expected = true,
    ) {

    companion object {
        const val MESSAGE = "{{cookiecutter.resource_name.capitalize()}} not exists"
        const val ERROR_KEY = "{{cookiecutter.resource_name.upper()}}_NOT_EXISTS"
    }
}