package br.com.caju.eventconsumer.person.dto

import br.com.caju.domain.person.model.Person
import java.util.UUID

data class PersonMessageListenerDTO(
    val id: UUID,
    val name: String,
    val cpf: String,
    val email: String,
    val type: PersonEventListenerTypeDTO,
) {
    fun toModel() = Person(id = id, name = name, cpf = cpf, email = email)
}

enum class PersonEventListenerTypeDTO {
    CREATE,
    UPDATE,
}
