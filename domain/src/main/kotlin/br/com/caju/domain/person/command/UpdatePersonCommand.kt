package br.com.caju.domain.person.command

import br.com.caju.domain.person.model.Person
import java.util.UUID

data class UpdatePersonCommand(val id: UUID, val name: String, val email: String, val cpf: String)

fun UpdatePersonCommand.toModel() = Person(id = id, name = name, email = email, cpf = cpf)
