package br.com.caju.domain.person.usecase

import br.com.caju.domain.person.exception.PersonNotExistsException
import br.com.caju.domain.person.model.Person
import br.com.caju.domain.person.port.driven.PersonDataAccessPort
import br.com.caju.domain.person.port.driver.GetPersonByIdPort
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class GetPersonByIdUseCase(private val personDataAccessPort: PersonDataAccessPort) :
    GetPersonByIdPort {
    override suspend fun getById(id: UUID): Person =
        personDataAccessPort.findById(id) ?: throw PersonNotExistsException(id)
}
