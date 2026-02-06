{% if cookiecutter.include_kafka_events == 'y' %}package br.com.caju.{{cookiecutter._resource_name_package}}.listener

import br.com.caju.domain.shared.log.logger
import br.com.caju.{{cookiecutter._resource_name_package}}.dto.{{cookiecutter._resource_name_class}}MessageListenerDTO
import br.com.caju.shared.config.KafkaConstants.KAFKA_OPERATION_NAME
import br.com.caju.shared.config.KafkaConstants.OFFSET_RESET_EARLIEST
import datadog.trace.api.Trace
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service

@Service
class {{cookiecutter._resource_name_class}}MessageListener {

    @Trace(
        resourceName = TP_{{cookiecutter._resource_name_constant}}_PRODUCER_TOPIC,
        operationName = KAFKA_OPERATION_NAME,
        noParent = true,
    )
    @KafkaListener(
        groupId = "cg_{{cookiecutter._resource_name_package}}_event_create",
        id = "cl_{{cookiecutter._resource_name_package}}_event_create",
        topics = [TP_{{cookiecutter._resource_name_constant}}_PRODUCER_TOPIC],
        properties = [OFFSET_RESET_EARLIEST],
    )
    fun receive(@Payload {{cookiecutter._resource_name_package}}MessageListenerDTO: {{cookiecutter._resource_name_class}}MessageListenerDTO) {{ "{" }}{% if cookiecutter.resource_name == 'Article' %}
        logger.info("{{cookiecutter._resource_name_class}} Event consumed=${{cookiecutter._resource_name_package}}MessageListenerDTO")
        // TODO: Process the article event - call domain use case
{% else %}
        logger.info("{{cookiecutter._resource_name_class}} Event consumed=${{cookiecutter._resource_name_package}}MessageListenerDTO")
        // TODO: Process the {{cookiecutter._resource_name_package}} event - call your domain use case here
{% endif %}
    }

    companion object {
        private const val TP_{{cookiecutter._resource_name_constant}}_PRODUCER_TOPIC = "tp_{{cookiecutter._resource_name_package}}_event_create"
        private val logger = logger()
    }
}{% else %}
// Kafka events are disabled for this resource{% endif %}