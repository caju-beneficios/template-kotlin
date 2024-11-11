package br.com.caju.domain.person.exception

import br.com.caju.domain.shared.exception.AppException

data class PersonAlreadyExistsException(
    val cpf: String
): AppException.AlreadyExistsException(
    message = MESSAGE,
    errorKey = ERROR_KEY,
    data =  mapOf("cpf" to cpf),
    expected = true
) {

    companion object {
        const val MESSAGE = "Person already exists"
        const val ERROR_KEY = "PERSON_ALREADY_EXISTS"
    }
}
