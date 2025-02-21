package br.com.caju.domain.person.model

import java.util.UUID

data class Person(val id: UUID, val name: String, val email: String, val cpf: String)
