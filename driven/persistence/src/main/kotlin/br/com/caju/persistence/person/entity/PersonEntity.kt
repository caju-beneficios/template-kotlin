package br.com.caju.persistence.person.entity

import br.com.caju.domain.person.model.Person
import br.com.caju.persistence.shared.jpa.AuditableFull
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Version
import java.util.UUID
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes.VARCHAR

@Entity(name = "Person")
@Table
data class PersonEntity(
    @Id @JdbcTypeCode(VARCHAR) val id: UUID,
    @Version var version: Long? = null,
    val name: String,
    val email: String,
    val cpf: String,
) : AuditableFull()

fun PersonEntity.merge(person: Person) =
    copy(name = person.name, email = person.email, cpf = person.cpf)

fun PersonEntity.toModel() = Person(id = id, name = name, email = email, cpf = cpf)

fun Person.toEntity() = PersonEntity(id = id, name = name, email = email, cpf = cpf)
