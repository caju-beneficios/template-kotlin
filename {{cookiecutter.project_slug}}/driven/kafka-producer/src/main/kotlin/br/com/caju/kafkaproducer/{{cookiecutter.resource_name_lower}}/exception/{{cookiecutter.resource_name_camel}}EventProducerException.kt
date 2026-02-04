package br.com.caju.kafkaproducer.{{cookiecutter.resource_name.lower()}}.exception{% if cookiecutter.include_kafka_events == 'y' %}

import br.com.caju.kafkaproducer.shared.exception.EventProducerException
import java.util.UUID

data class {{cookiecutter.resource_name_camel}}EventProducerException(val {{cookiecutter.resource_name.lower()}}Id: UUID) :
    EventProducerException(
        message = MESSAGE,
        errorKey = ERROR_KEY,
        data = mapOf("{{cookiecutter.resource_name.lower()}}Id" to {{cookiecutter.resource_name.lower()}}Id.toString()),
    ) {
    companion object {
        const val MESSAGE = "Error on produce event to tp_{{cookiecutter.resource_name.lower()}}"
        const val ERROR_KEY = "{{cookiecutter.resource_name.upper()}}_EVENT_PRODUCER_ERROR"
    }
}{% else %}

// Kafka events are disabled for this resource{% endif %}