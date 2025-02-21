package br.com.caju.domain.person.usecase

import br.com.caju.domain.person.command.CreatePersonCommand
import br.com.caju.domain.person.command.toModel
import br.com.caju.domain.person.exception.PersonAlreadyExistsException
import br.com.caju.domain.person.exception.PersonExternalNotExistsException
import br.com.caju.domain.person.model.Person
import br.com.caju.domain.person.model.PersonEventType.CREATE
import br.com.caju.domain.person.model.PersonLogVar.PERSON_ID
import br.com.caju.domain.person.port.driven.PersonDataAccessPort
import br.com.caju.domain.person.port.driven.PersonEventProducerPort
import br.com.caju.domain.person.port.driven.PersonRestClientPort
import br.com.caju.domain.person.port.driver.CreatePersonPort
import br.com.caju.domain.shared.log.annotate
import br.com.caju.domain.shared.log.logger
import br.com.caju.domain.shared.transaction.port.driven.TransactionPort
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class CreatePersonUseCase(
    private val personRestClientPort: PersonRestClientPort,
    private val personDataAccessPort: PersonDataAccessPort,
    private val personEventProducerPort: PersonEventProducerPort,
    private val transactionPort: TransactionPort,
) : CreatePersonPort {
    override suspend fun invoke(command: CreatePersonCommand): Person = run {
        val personId = UUID.randomUUID()

        logger.annotate(PERSON_ID, personId).info("Creating person")

        checkExternalPersonExists(command)
        checkPersonExists(command)

        saveAndNotify(command.toModel(personId))
    }

    private suspend fun checkExternalPersonExists(command: CreatePersonCommand) {
        personRestClientPort.findBy(command.cpf).takeIf { it != null }
            ?: let { throw PersonExternalNotExistsException(command.cpf) }
    }

    private suspend fun checkPersonExists(command: CreatePersonCommand) {
        personDataAccessPort
            .existsByCpf(command.cpf)
            .takeIf { it }
            ?.let { throw PersonAlreadyExistsException(command.cpf) }
    }

    private suspend fun saveAndNotify(person: Person) = transactionPort {
        personDataAccessPort.save(person).also { personSaved ->
            logger.info("Person Saved")
            personEventProducerPort.notifyMessage(person = personSaved, personEventType = CREATE)
        }
    }

    companion object {
        private val logger = logger()
    }
}
