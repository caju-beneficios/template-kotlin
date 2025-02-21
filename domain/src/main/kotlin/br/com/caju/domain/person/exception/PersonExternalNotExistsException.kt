package br.com.caju.domain.person.exception

import br.com.caju.domain.shared.exception.AppException

data class PersonExternalNotExistsException(val cpf: String) :
    AppException.NotFoundException(
        message = MESSAGE,
        errorKey = ERROR_KEY,
        data = mapOf("cpf" to cpf),
        expected = true,
    ) {

    companion object {
        const val MESSAGE = "Person external not exists"
        const val ERROR_KEY = "PERSON_EXTERNAL_NOT_EXISTS"
    }
}
