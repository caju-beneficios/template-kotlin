package br.com.caju.eventproducer.person.exception

import br.com.caju.eventproducer.shared.exception.EventProducerException
import java.util.UUID

data class PersonEventProducerException(val personId: UUID) :
    EventProducerException(
        message = MESSAGE,
        errorKey = ERROR_KEY,
        data = mapOf("personId" to personId.toString()),
    ) {
    companion object {
        const val MESSAGE = "Error on produce event to tp_person"
        const val ERROR_KEY = "PERSON_EVENT_PRODUCER_ERROR"
    }
}
