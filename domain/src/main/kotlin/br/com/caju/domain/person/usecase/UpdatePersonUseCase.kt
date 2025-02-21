package br.com.caju.domain.person.usecase

import br.com.caju.domain.person.command.UpdatePersonCommand
import br.com.caju.domain.person.command.toModel
import br.com.caju.domain.person.exception.PersonNotExistsException
import br.com.caju.domain.person.model.Person
import br.com.caju.domain.person.model.PersonEventType.UPDATE
import br.com.caju.domain.person.model.PersonLogVar.PERSON_ID
import br.com.caju.domain.person.port.driven.PersonDataAccessPort
import br.com.caju.domain.person.port.driven.PersonEventProducerPort
import br.com.caju.domain.person.port.driver.UpdatePersonPort
import br.com.caju.domain.shared.log.annotate
import br.com.caju.domain.shared.log.logger
import br.com.caju.domain.shared.transaction.port.driven.TransactionPort
import org.springframework.stereotype.Service

@Service
class UpdatePersonUseCase(
    private val personDataAccessPort: PersonDataAccessPort,
    private val personEventProducerPort: PersonEventProducerPort,
    private val transactionPort: TransactionPort,
) : UpdatePersonPort {
    override suspend fun invoke(command: UpdatePersonCommand): Person = run {
        logger.annotate(PERSON_ID, command.id)
        personDataAccessPort.findByCpf(command.cpf)?.let { updateAndNotify(command.toModel()) }
            ?: throw PersonNotExistsException(command.id)
    }

    private suspend fun updateAndNotify(person: Person) = transactionPort {
        personDataAccessPort.update(person).also { personSaved ->
            logger.info("Person Updated")
            personEventProducerPort.notifyMessage(person = personSaved, personEventType = UPDATE)
        }
    }

    companion object {
        private val logger = logger()
    }
}
