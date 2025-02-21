package br.com.caju.eventproducer.person.dto

import br.com.caju.domain.person.model.Person
import br.com.caju.domain.person.model.PersonEventType
import java.util.UUID

data class PersonMessageDTO(
    val id: UUID,
    val name: String,
    val cpf: String,
    val email: String,
    val type: PersonEventTypeDTO,
)

enum class PersonEventTypeDTO {
    CREATE,
    UPDATE,
}

fun PersonEventType.toDTO() = PersonEventTypeDTO.valueOf(name)

fun Person.toEventCreate(personEventType: PersonEventType) =
    PersonMessageDTO(id = id, name = name, cpf = cpf, email = email, type = personEventType.toDTO())
