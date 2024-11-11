package br.com.caju.domain.person.port.driver

import br.com.caju.domain.person.command.CreatePersonCommand
import br.com.caju.domain.person.model.Person

fun interface CreatePersonPort {
    suspend operator fun invoke(command: CreatePersonCommand): Person
}
