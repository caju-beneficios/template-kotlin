package br.com.caju.persistence.person.repository

import br.com.caju.persistence.person.entity.PersonEntity
import br.com.caju.persistence.shared.jpa.Repository.ReadRepository
import br.com.caju.persistence.shared.jpa.Repository.WriteRepository
import java.util.UUID
import org.springframework.stereotype.Repository

@Repository
interface PersonReadRepository : ReadRepository<PersonEntity, UUID> {
    fun findByCpf(cpf: String): PersonEntity?

    fun existsByCpf(cpf: String): Boolean
}

@Repository interface PersonWriteRepository : WriteRepository<PersonEntity, UUID> {}
