package br.com.caju.domain.person.usecase

import br.com.caju.domain.person.exception.PersonNotExistsException
import br.com.caju.domain.person.model.Person
import br.com.caju.domain.person.port.driven.PersonDataAccessPort
import br.com.caju.domain.person.port.driver.GetPersonByIdPort
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GetPersonByIdUseCase(
    private val personDataAccessPort: PersonDataAccessPort
) : GetPersonByIdPort {
    override suspend fun getById(id: UUID): Person =
        personDataAccessPort.findById(id) ?: throw PersonNotExistsException(id)
}
