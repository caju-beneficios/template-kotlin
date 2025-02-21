package br.com.caju.domain.person.port.driven

import br.com.caju.domain.person.model.Person

interface PersonRestClientPort {
    suspend fun findBy(cpf: String): Person?
}
