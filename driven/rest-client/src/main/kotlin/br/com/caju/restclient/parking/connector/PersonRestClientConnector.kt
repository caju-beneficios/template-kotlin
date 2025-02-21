package br.com.caju.restclient.parking.connector

import br.com.caju.domain.person.model.Person
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange

@HttpExchange(url = "\${rest-client.person.url.base}", accept = [APPLICATION_JSON_VALUE])
fun interface PersonRestClientConnector {

    @GetExchange("\${rest-client.person.url.context-path}")
    suspend fun findBy(@RequestParam cpf: String): ResponseEntity<Person>?
}
