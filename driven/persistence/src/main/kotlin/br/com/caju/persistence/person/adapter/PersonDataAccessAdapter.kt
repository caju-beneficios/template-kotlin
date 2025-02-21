package br.com.caju.persistence.person.adapter

import br.com.caju.domain.person.model.Person
import br.com.caju.domain.person.port.driven.PersonDataAccessPort
import br.com.caju.domain.shared.pagination.model.PaginatedModel
import br.com.caju.domain.shared.pagination.model.Pagination
import br.com.caju.persistence.person.entity.merge
import br.com.caju.persistence.person.entity.toEntity
import br.com.caju.persistence.person.entity.toModel
import br.com.caju.persistence.person.repository.PersonReadRepository
import br.com.caju.persistence.person.repository.PersonWriteRepository
import br.com.caju.persistence.shared.context.PersistenceContext
import br.com.caju.persistence.shared.entity.requiredEntity
import br.com.caju.persistence.shared.pagination.toPageable
import br.com.caju.persistence.shared.pagination.toPaginatedModel
import datadog.trace.api.Trace
import java.util.UUID
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Service

@Service
class PersonDataAccessAdapter(
    private val personWriteRepository: PersonWriteRepository,
    private val personReadRepository: PersonReadRepository,
    private val context: PersistenceContext,
) : PersonDataAccessPort {

    @Trace
    override suspend fun save(person: Person): Person = context {
        personWriteRepository.save(person.toEntity()).toModel()
    }

    @Trace
    override suspend fun update(person: Person): Person = context {
        personReadRepository.findById(person.id).getOrNull().requiredEntity(person.id).run {
            personWriteRepository.save(merge(person)).toModel()
        }
    }

    @Trace
    override suspend fun findAll(pagination: Pagination): PaginatedModel<Person> = context {
        personReadRepository.findAll(pagination.toPageable()).toPaginatedModel { it.toModel() }
    }

    @Trace
    override suspend fun findByCpf(cpf: String): Person? = context {
        personReadRepository.findByCpf(cpf)?.toModel()
    }

    @Trace
    override suspend fun findById(id: UUID): Person? = context {
        personReadRepository.findById(id).getOrNull()?.toModel()
    }

    @Trace
    override suspend fun existsByCpf(cpf: String): Boolean = context {
        personReadRepository.existsByCpf(cpf)
    }
}
