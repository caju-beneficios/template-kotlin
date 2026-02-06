package br.com.caju.kafkaproducer.{{cookiecutter.resource_name_lower}}.adapter

{% if cookiecutter.include_kafka_events == 'y' %}
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model.{{cookiecutter.resource_name_class}}
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model.{{cookiecutter.resource_name_class}}EventType
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.port.driven.{{cookiecutter.resource_name_class}}EventProducerPort
import br.com.caju.domain.shared.log.logger
import br.com.caju.kafkaproducer.{{cookiecutter.resource_name_lower}}.dto.toEventMessage
import br.com.caju.kafkaproducer.{{cookiecutter.resource_name_lower}}.exception.{{cookiecutter.resource_name_class}}EventProducerException
import br.com.caju.kafkaproducer.shared.sendCorrelated
import datadog.trace.api.Trace
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class {{cookiecutter.resource_name_class}}EventProducerAdapter(private val kafkaTemplate: KafkaTemplate<String, Any>) :
    {{cookiecutter.resource_name_class}}EventProducerPort {

    @Trace
    override suspend fun notifyMessage({{cookiecutter.resource_name_lower}}: {{cookiecutter.resource_name_class}}, {{cookiecutter.resource_name_lower}}EventType: {{cookiecutter.resource_name_class}}EventType) {
        runCatching {
                logger.info("Sending {{cookiecutter.resource_name_lower}} event")
                kafkaTemplate.sendCorrelated(
                    topic = TP_{{cookiecutter.resource_name_constant}},
                    key = {{cookiecutter.resource_name_lower}}.id.toString(),
                    value = {{cookiecutter.resource_name_lower}}.toEventMessage({{cookiecutter.resource_name_lower}}EventType),
                )
            }
            .onSuccess { logger.info("{{cookiecutter.resource_name_class}} event sent") }
            .onFailure { error ->
                logger.error("Could not send {{cookiecutter.resource_name_lower}} event", error)
                throw {{cookiecutter.resource_name_class}}EventProducerException({{cookiecutter.resource_name_lower}}.id)
            }
            .getOrThrow()
    }

    companion object {
        const val TP_{{cookiecutter.resource_name_constant}} = "tp_{{cookiecutter.resource_name_lower}}"
        private val logger = logger()
    }
}
{% else %}
// Kafka events are disabled for this resource
{% endif %}