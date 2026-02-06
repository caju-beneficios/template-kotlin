package br.com.caju.postgresqlpersistence.shared.utils

import br.com.caju.domain.person.model.Person
import br.com.caju.postgresqlpersistence.person.entity.toEntity
import br.com.caju.postgresqlpersistence.person.entity.toModel
import br.com.caju.postgresqlpersistence.person.repository.PersonWriteRepository
import org.springframework.stereotype.Component

@Component
class RepositoryUtils(private val personWriteRepository: PersonWriteRepository) {
    fun savePerson(person: Person) = personWriteRepository.save(person.toEntity()).toModel()
}
