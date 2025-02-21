package br.com.caju.domain.person.usecase

import br.com.caju.domain.person.model.Person
import br.com.caju.domain.person.port.driven.PersonDataAccessPort
import br.com.caju.domain.person.port.driver.GetAllPersonPaginatedPort
import br.com.caju.domain.shared.pagination.model.PaginatedModel
import br.com.caju.domain.shared.pagination.model.Pagination
import org.springframework.stereotype.Service

@Service
class GetAllPersonPaginatedUseCase(private val personDataAccessPort: PersonDataAccessPort) :
    GetAllPersonPaginatedPort {
    override suspend fun getAll(pagination: Pagination): PaginatedModel<Person> =
        personDataAccessPort.findAll(pagination)
}
