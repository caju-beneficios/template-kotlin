package br.com.caju.domain.person.exception

import br.com.caju.domain.shared.exception.AppException
import java.util.UUID

data class PersonEventProducerException(val personId: UUID) : AppException.GeneralException(
    message = MESSAGE,
    errorKey = ERROR_KEY,
    data =  mapOf("personId" to personId)
) {

    companion object {
        const val MESSAGE = "Error on produce event to person"
        const val ERROR_KEY = "PERSON_EVENT_PRODUCER_ERROR"
    }
}
