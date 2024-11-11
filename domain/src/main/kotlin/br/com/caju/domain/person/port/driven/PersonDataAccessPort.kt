package br.com.caju.domain.person.port.driven

import br.com.caju.domain.person.model.Person
import br.com.caju.domain.shared.pagination.model.PaginatedModel
import br.com.caju.domain.shared.pagination.model.Pagination
import org.springframework.context.annotation.Primary
import java.util.UUID

interface PersonDataAccessPort {
     fun save(person: Person): Person

     fun update(person: Person): Person

     fun findAll(pagination: Pagination): PaginatedModel<Person>

     fun findByCpf(cpf: String): Person?

     fun findById(id: UUID): Person?

     fun existsByCpf(cpf: String): Boolean
}
