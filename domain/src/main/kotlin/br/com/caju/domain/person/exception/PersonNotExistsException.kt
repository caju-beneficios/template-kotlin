package br.com.caju.domain.person.exception

import br.com.caju.domain.shared.exception.AppException
import java.util.UUID

data class PersonNotExistsException(
    val personId: UUID
): AppException.NotFoundException(
    message = MESSAGE,
    errorKey = ERROR_KEY,
    data =  mapOf("personId" to personId),
    expected = true
) {

    companion object {
        const val MESSAGE = "Person not exists"
        const val ERROR_KEY = "PERSON_NOT_EXISTS"
    }
}

