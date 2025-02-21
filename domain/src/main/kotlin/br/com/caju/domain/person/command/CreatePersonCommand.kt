package br.com.caju.domain.person.command

import br.com.caju.domain.person.model.Person
import java.util.UUID

data class CreatePersonCommand(val name: String, val email: String, val cpf: String)

fun CreatePersonCommand.toModel(id: UUID) = Person(id = id, name = name, email = email, cpf = cpf)
