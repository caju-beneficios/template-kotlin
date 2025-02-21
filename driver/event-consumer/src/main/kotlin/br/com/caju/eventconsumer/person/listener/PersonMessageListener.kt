package br.com.caju.eventconsumer.person.listener

import br.com.caju.domain.shared.log.logger
import br.com.caju.eventconsumer.person.dto.PersonMessageListenerDTO
import br.com.caju.eventconsumer.shared.config.KafkaConstants.KAFKA_OPERATION_NAME
import br.com.caju.eventconsumer.shared.config.KafkaConstants.OFFSET_RESET_EARLIEST
import datadog.trace.api.Trace
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service

@Service
class PersonMessageListener {

    @Trace(
        resourceName = TP_PERSON_PRODUCER_TOPIC,
        operationName = KAFKA_OPERATION_NAME,
        noParent = true,
    )
    @KafkaListener(
        groupId = "cg_person_event_create",
        id = "cl_person_event_create",
        topics = [TP_PERSON_PRODUCER_TOPIC],
        properties = [OFFSET_RESET_EARLIEST],
    )
    fun receive(@Payload personMessageListenerDTO: PersonMessageListenerDTO) {
        logger.info("Person Event consumed=$personMessageListenerDTO")
    }

    companion object {
        private const val TP_PERSON_PRODUCER_TOPIC = "tp_person_event_create"
        private val logger = logger()
    }
}
