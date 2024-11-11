package br.com.caju.domain.person.port.driver

import br.com.caju.domain.person.command.UpdatePersonCommand
import br.com.caju.domain.person.model.Person

fun interface UpdatePersonPort {
    suspend operator fun invoke(command: UpdatePersonCommand): Person
}
