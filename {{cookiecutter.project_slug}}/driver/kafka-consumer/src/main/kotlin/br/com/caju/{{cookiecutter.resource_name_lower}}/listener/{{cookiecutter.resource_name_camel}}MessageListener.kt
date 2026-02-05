{% if cookiecutter.include_kafka_events == 'y' %}package br.com.caju.{{cookiecutter.resource_name_lower}}.listener

import br.com.caju.domain.shared.log.logger
import br.com.caju.{{cookiecutter.resource_name_lower}}.dto.{{cookiecutter.resource_name_camel}}MessageListenerDTO
import br.com.caju.shared.config.KafkaConstants.KAFKA_OPERATION_NAME
import br.com.caju.shared.config.KafkaConstants.OFFSET_RESET_EARLIEST
import datadog.trace.api.Trace
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service

@Service
class {{cookiecutter.resource_name_camel}}MessageListener {

    @Trace(
        resourceName = TP_{{cookiecutter.resource_name.upper()}}_PRODUCER_TOPIC,
        operationName = KAFKA_OPERATION_NAME,
        noParent = true,
    )
    @KafkaListener(
        groupId = "cg_{{cookiecutter.resource_name_lower}}_event_create",
        id = "cl_{{cookiecutter.resource_name_lower}}_event_create",
        topics = [TP_{{cookiecutter.resource_name.upper()}}_PRODUCER_TOPIC],
        properties = [OFFSET_RESET_EARLIEST],
    )
    fun receive(@Payload {{cookiecutter.resource_name_lower}}MessageListenerDTO: {{cookiecutter.resource_name_camel}}MessageListenerDTO) {{ "{" }}{% if cookiecutter.resource_name == 'Article' %}
        logger.info("{{cookiecutter.resource_name}} Event consumed=${{cookiecutter.resource_name_lower}}MessageListenerDTO")
        // TODO: Process the article event - call domain use case
{% else %}
        logger.info("{{cookiecutter.resource_name}} Event consumed=${{cookiecutter.resource_name_lower}}MessageListenerDTO")
        // TODO: Process the {{cookiecutter.resource_name_lower}} event - call your domain use case here
{% endif %}
    }

    companion object {
        private const val TP_{{cookiecutter.resource_name.upper()}}_PRODUCER_TOPIC = "tp_{{cookiecutter.resource_name_lower}}_event_create"
        private val logger = logger()
    }
}{% else %}
// Kafka events are disabled for this resource{% endif %}