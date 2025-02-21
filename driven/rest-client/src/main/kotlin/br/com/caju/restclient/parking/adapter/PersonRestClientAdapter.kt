package br.com.caju.restclient.parking.adapter

import br.com.caju.domain.person.model.Person
import br.com.caju.domain.person.port.driven.PersonRestClientPort
import br.com.caju.restclient.parking.connector.PersonRestClientConnector
import datadog.trace.api.Trace
import org.springframework.stereotype.Component

@Component
class PersonRestClientAdapter(private val personRestClientConnector: PersonRestClientConnector) :
    PersonRestClientPort {

    @Trace
    override suspend fun findBy(cpf: String): Person? = personRestClientConnector.findBy(cpf)?.body
}
