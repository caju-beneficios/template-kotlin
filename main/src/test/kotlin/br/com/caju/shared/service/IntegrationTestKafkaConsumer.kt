package br.com.caju.shared.service

import com.fasterxml.jackson.databind.ObjectMapper
import java.time.Duration
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.RequiresOptIn.Level.WARNING
import kotlin.annotation.AnnotationRetention.BINARY
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.LOCAL_VARIABLE
import kotlin.annotation.AnnotationTarget.PROPERTY
import kotlin.annotation.AnnotationTarget.VALUE_PARAMETER
import kotlin.reflect.KClass
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.utils.KafkaTestUtils
import org.springframework.stereotype.Service

@Service
class IntegrationTestKafkaConsumer(
    private val embeddedKafkaBroker: EmbeddedKafkaBroker,
    val objectMapper: ObjectMapper,
) {

    val consumer =
        DefaultKafkaConsumerFactory(
                KafkaTestUtils.consumerProps(GROUP_ID, AUTO_COMMIT, embeddedKafkaBroker),
                StringDeserializer(),
                StringDeserializer(),
            )
            .createConsumer()

    val isConsumerStarted = AtomicBoolean()

    fun start() {
        if (!isConsumerStarted.get()) {
            embeddedKafkaBroker.consumeFromAllEmbeddedTopics(consumer)
            isConsumerStarted.set(true)
        }
    }

    fun stop() {
        if (isConsumerStarted.get()) {
            embeddedKafkaBroker.destroy()
        }
    }

    companion object {
        const val GROUP_ID = "cg_test_consumer"
        const val AUTO_COMMIT = "false"
    }
}

@DelicatedKafkaTestFunction
inline fun <reified V> IntegrationTestKafkaConsumer.consumeMessage(
    topic: String,
    duration: Duration = Duration.ofSeconds(1),
    block: (String, V) -> Unit,
) {
    val record = KafkaTestUtils.getSingleRecord(consumer, topic, duration)
    val key = record.key()
    val value: V =
        if (!isNative(V::class)) {
            objectMapper.readValue<V>(record.value(), V::class.java)
        } else {
            parseNativeValue(record.value(), V::class) as V
        }

    runCatching { block(key, value) }.getOrNull().also { consumer.commitAsync() }
}

fun isNative(clazz: KClass<*>): Boolean {
    return clazz in
        setOf(
            Int::class,
            Long::class,
            Double::class,
            Float::class,
            Boolean::class,
            Char::class,
            Byte::class,
            Short::class,
            String::class,
        )
}

fun parseNativeValue(value: String, clazz: KClass<*>): Any {
    return when (clazz) {
        Int::class -> value.toInt()
        Long::class -> value.toLong()
        Double::class -> value.toDouble()
        Float::class -> value.toFloat()
        Boolean::class -> value.toBoolean()
        Char::class -> value.first()
        Byte::class -> value.toByte()
        Short::class -> value.toShort()
        String::class -> value
        else -> throw IllegalArgumentException("Unsupported type")
    }
}

@Retention(BINARY)
@Target(FUNCTION, PROPERTY, LOCAL_VARIABLE, VALUE_PARAMETER, CLASS)
@RequiresOptIn(
    level = WARNING,
    message =
        "This API must not be used within an `eventually` block. Consider using it in before or after instead.",
)
annotation class DelicatedKafkaTestFunction
