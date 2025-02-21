package br.com.caju.eventproducer.person.adapter

import br.com.caju.domain.person.model.Person
import br.com.caju.domain.person.model.PersonEventType
import br.com.caju.domain.person.port.driven.PersonEventProducerPort
import br.com.caju.domain.shared.log.logger
import br.com.caju.eventproducer.person.dto.toEventCreate
import br.com.caju.eventproducer.person.exception.PersonEventProducerException
import br.com.caju.eventproducer.shared.sendCorrelated
import datadog.trace.api.Trace
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class PersonEventProducerAdapter(private val kafkaTemplate: KafkaTemplate<String, Any>) :
    PersonEventProducerPort {

    @Trace
    override suspend fun notifyMessage(person: Person, personEventType: PersonEventType) {
        runCatching {
                logger.info("Sending person event")
                kafkaTemplate.sendCorrelated(
                    topic = TP_PERSON,
                    key = person.id.toString(),
                    value = person.toEventCreate(personEventType),
                )
            }
            .onSuccess { logger.info("Person event sent") }
            .onFailure { error ->
                logger.error("Could not send person event", error)
                throw PersonEventProducerException(person.id)
            }
            .getOrThrow()
    }

    companion object {
        const val TP_PERSON = "tp_person"
        private val logger = logger()
    }
}
