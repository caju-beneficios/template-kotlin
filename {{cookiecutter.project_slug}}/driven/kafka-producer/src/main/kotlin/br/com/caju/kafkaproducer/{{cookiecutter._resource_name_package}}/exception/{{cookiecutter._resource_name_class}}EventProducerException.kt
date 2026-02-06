package br.com.caju.kafkaproducer.{{cookiecutter._resource_name_package}}.exception{% if cookiecutter.include_kafka_events == 'y' %}

import br.com.caju.kafkaproducer.shared.exception.EventProducerException
import java.util.UUID

data class {{cookiecutter._resource_name_class}}EventProducerException(val {{cookiecutter._resource_name_package}}Id: UUID) :
    EventProducerException(
        message = MESSAGE,
        errorKey = ERROR_KEY,
        data = mapOf("{{cookiecutter._resource_name_package}}Id" to {{cookiecutter._resource_name_package}}Id.toString()),
    ) {
    companion object {
        const val MESSAGE = "Error on produce event to tp_{{cookiecutter._resource_name_package}}"
        const val ERROR_KEY = "{{cookiecutter._resource_name_constant}}_EVENT_PRODUCER_ERROR"
    }
}{% else %}

// Kafka events are disabled for this resource{% endif %}