package br.com.caju.domain.person.port.driven

import br.com.caju.domain.person.model.Person
import br.com.caju.domain.shared.pagination.model.PaginatedModel
import br.com.caju.domain.shared.pagination.model.Pagination
import java.util.UUID

interface PersonDataAccessPort {
    suspend fun save(person: Person): Person

    suspend fun update(person: Person): Person

    suspend fun findAll(pagination: Pagination): PaginatedModel<Person>

    suspend fun findByCpf(cpf: String): Person?

    suspend fun findById(id: UUID): Person?

    suspend fun existsByCpf(cpf: String): Boolean
}
