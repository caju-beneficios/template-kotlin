package br.com.caju.restserver.person.dto

import br.com.caju.domain.person.command.CreatePersonCommand
import br.com.caju.domain.person.command.UpdatePersonCommand
import br.com.caju.domain.person.model.Person
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size
import java.util.UUID

data class PersonRequestDTO(
    @field:Size(min = 3, max = 100) val name: String,
    @field:Email val email: String,
    val cpf: String,
) {
    fun toCreateCommand() = CreatePersonCommand(name = name, email = email, cpf = cpf)

    fun toUpdateCommand(id: UUID) =
        UpdatePersonCommand(id = id, name = name, email = email, cpf = cpf)
}

data class PersonResponseDTO(val id: UUID?, val name: String, val email: String, val cpf: String)

fun Person.toDTO() = PersonResponseDTO(id = id, name = name, email = email, cpf = cpf)
