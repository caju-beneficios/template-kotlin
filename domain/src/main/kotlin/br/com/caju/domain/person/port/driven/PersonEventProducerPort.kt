package br.com.caju.domain.person.port.driven

import br.com.caju.domain.person.model.Person
import br.com.caju.domain.person.model.PersonEventType

interface PersonEventProducerPort {
    suspend fun notifyMessage(person: Person, personEventType: PersonEventType)
}
