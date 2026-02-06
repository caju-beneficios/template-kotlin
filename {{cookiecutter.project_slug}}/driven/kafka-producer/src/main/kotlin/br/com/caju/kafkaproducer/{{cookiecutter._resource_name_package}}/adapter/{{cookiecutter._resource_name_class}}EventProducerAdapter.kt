package br.com.caju.kafkaproducer.{{cookiecutter._resource_name_package}}.adapter{% if cookiecutter.include_kafka_events == 'y' %}

import br.com.caju.domain.{{cookiecutter._resource_name_package}}.model.{{cookiecutter._resource_name_class}}
import br.com.caju.domain.{{cookiecutter._resource_name_package}}.model.{{cookiecutter._resource_name_class}}EventType
import br.com.caju.domain.{{cookiecutter._resource_name_package}}.port.driven.{{cookiecutter._resource_name_class}}EventProducerPort
import br.com.caju.domain.shared.log.logger
import br.com.caju.kafkaproducer.{{cookiecutter._resource_name_package}}.dto.toEventMessage
import br.com.caju.kafkaproducer.{{cookiecutter._resource_name_package}}.exception.{{cookiecutter._resource_name_class}}EventProducerException
import br.com.caju.kafkaproducer.shared.sendCorrelated
import datadog.trace.api.Trace
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class {{cookiecutter._resource_name_class}}EventProducerAdapter(private val kafkaTemplate: KafkaTemplate<String, Any>) :
    {{cookiecutter._resource_name_class}}EventProducerPort {

    @Trace
    override suspend fun notifyMessage({{cookiecutter._resource_name_package}}: {{cookiecutter._resource_name_class}}, {{cookiecutter._resource_name_package}}EventType: {{cookiecutter._resource_name_class}}EventType) {
        runCatching {
                logger.info("Sending {{cookiecutter._resource_name_package}} event")
                kafkaTemplate.sendCorrelated(
                    topic = TP_{{cookiecutter._resource_name_constant}},
                    key = {{cookiecutter._resource_name_package}}.id.toString(),
                    value = {{cookiecutter._resource_name_package}}.toEventMessage({{cookiecutter._resource_name_package}}EventType),
                )
            }
            .onSuccess { logger.info("{{cookiecutter._resource_name_class}} event sent") }
            .onFailure { error ->
                logger.error("Could not send {{cookiecutter._resource_name_package}} event", error)
                throw {{cookiecutter._resource_name_class}}EventProducerException({{cookiecutter._resource_name_package}}.id)
            }
            .getOrThrow()
    }

    companion object {
        const val TP_{{cookiecutter._resource_name_constant}} = "tp_{{cookiecutter._resource_name_package}}"
        private val logger = logger()
    }
}{% else %}

// Kafka events are disabled for this resource{% endif %}