package br.com.caju.domain.person.port.driver

import br.com.caju.domain.person.model.Person
import java.util.UUID

fun interface GetPersonByIdPort {
    suspend fun getById(id: UUID): Person
}
