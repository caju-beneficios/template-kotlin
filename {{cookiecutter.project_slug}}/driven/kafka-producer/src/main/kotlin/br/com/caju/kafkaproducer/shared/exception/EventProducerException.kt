package br.com.caju.kafkaproducer.shared.exception

import br.com.caju.domain.shared.exception.AppException.GeneralException

abstract class EventProducerException(
    override val message: String = MESSAGE,
    override val errorKey: String = ERROR_KEY,
    override val data: Map<String, String> = emptyMap(),
) : GeneralException(message = message, errorKey = errorKey, data = data) {

    companion object {
        const val MESSAGE = "Error on produce event to topic"
        const val ERROR_KEY = "GENERIC_EVENT_PRODUCER_ERROR"
    }
}