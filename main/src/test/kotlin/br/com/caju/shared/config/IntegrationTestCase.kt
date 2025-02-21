package br.com.caju.shared.config

import br.com.caju.eventproducer.person.adapter.PersonEventProducerAdapter.Companion.TP_PERSON
import br.com.caju.shared.service.IntegrationTestKafkaConsumer
import br.com.caju.shared.utils.DatabaseCleanUpService
import com.github.tomakehurst.wiremock.WireMockServer
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.test.TestCaseOrder.Sequential
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.context.annotation.Import
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@Suppress("UNCHECKED_CAST")
@AutoConfigureWebMvc
@AutoConfigureWebTestClient
@Import(IntegrationTestConfiguration::class)
@ExtendWith(SpringExtension::class)
@EmbeddedKafka(topics = [TP_PERSON])
@SpringBootTest(webEnvironment = RANDOM_PORT)
abstract class IntegrationTestCase(body: IntegrationTestCase.() -> Unit = {}) :
    BehaviorSpec(body as BehaviorSpec.() -> Unit) {

    @Autowired lateinit var client: WebTestClient

    @Autowired lateinit var wiremock: WireMockServer

    @Autowired lateinit var producer: KafkaTemplate<String, Any?>

    @Autowired lateinit var consumer: IntegrationTestKafkaConsumer

    @Autowired lateinit var databaseCleanUpService: DatabaseCleanUpService

    override fun testCaseOrder() = Sequential

    override suspend fun beforeSpec(spec: Spec) = run {
        wiremock.resetMappings()
        consumer.start()
        databaseCleanUpService()
    }
}
