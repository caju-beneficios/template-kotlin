package br.com.caju.domain.{{cookiecutter.resource_name_lower}}.exception

import br.com.caju.domain.shared.exception.AppException
import java.util.UUID

data class {{cookiecutter.resource_name_class}}NotExistsException(val id: UUID) :
    AppException.NotFoundException(
        message = MESSAGE,
        errorKey = ERROR_KEY,
        data = mapOf("id" to id),
        expected = true,
    ) {

    companion object {
        const val MESSAGE = "{{cookiecutter.resource_name_class}} not exists"
        const val ERROR_KEY = "{{cookiecutter.resource_name_constant}}_NOT_EXISTS"
    }
}