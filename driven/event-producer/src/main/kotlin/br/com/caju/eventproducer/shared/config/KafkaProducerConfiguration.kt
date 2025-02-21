package br.com.caju.eventproducer.shared.config

import br.com.caju.domain.shared.config.ObjectMapperProvider.mapper
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.autoconfigure.kafka.KafkaConnectionDetails
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration
class KafkaConfiguration(
    private val kafkaProperties: KafkaProperties,
    private val kafkaConnectionDetails: KafkaConnectionDetails,
) {

    @Bean
    fun kafkaProducerFactory(): ProducerFactory<String, Any> =
        DefaultKafkaProducerFactory(
            kafkaProperties.buildProducerProperties(null).withConnectionDetails(),
            StringSerializer(),
            JsonSerializer(mapper),
        )

    @Bean
    fun kafkaTemplateFactory(
        kafkaProducer: ProducerFactory<String, Any>
    ): KafkaTemplate<String, Any> = KafkaTemplate(kafkaProducer)

    private fun MutableMap<String, Any>.withConnectionDetails() = apply {
        put("bootstrap.servers", kafkaConnectionDetails.producerBootstrapServers)
    }
}
