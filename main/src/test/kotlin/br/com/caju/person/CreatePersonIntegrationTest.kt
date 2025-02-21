package br.com.caju.person

import br.com.caju.domain.person.model.Person
import br.com.caju.domain.shared.config.ObjectMapperProvider.mapper
import br.com.caju.domain.shared.utils.random
import br.com.caju.eventproducer.person.adapter.PersonEventProducerAdapter.Companion.TP_PERSON
import br.com.caju.restserver.person.dto.PersonRequestDTO
import br.com.caju.shared.config.IntegrationTestCase
import br.com.caju.shared.service.DelicatedKafkaTestFunction
import br.com.caju.shared.service.consumeMessage
import br.com.caju.shared.utils.withStub
import io.kotest.matchers.shouldBe
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpStatus.OK
import org.springframework.test.web.reactive.server.expectBody

@OptIn(DelicatedKafkaTestFunction::class)
class CreatePersonIntegrationTest :
    IntegrationTestCase({
        Given("A person creation request") {
            val personRequest =
                PersonRequestDTO(
                    name = "John Doe",
                    email = "johndoe@email.com",
                    cpf = "98062264025",
                )

            val person =
                random<Person>()
                    .copy(
                        name = personRequest.name,
                        email = personRequest.email,
                        cpf = personRequest.cpf,
                    )

            wiremock.withStub(
                method = GET,
                url = "/person?cpf=${person.cpf}",
                httpStatus = OK,
                responseBody = mapper.writeValueAsString(person),
            )

            When("the request is valid") {
                Then("the person should be created") {
                    val response =
                        client
                            .post()
                            .uri("/quickstart/v1/person")
                            .bodyValue(personRequest)
                            .exchange()
                            .expectStatus()
                            .isOk
                            .expectBody<Person>()
                            .returnResult()
                            .responseBody!!

                    consumer.consumeMessage<Person>(TP_PERSON) { _, result ->
                        result shouldBe response
                    }
                }
            }
        }
    })
