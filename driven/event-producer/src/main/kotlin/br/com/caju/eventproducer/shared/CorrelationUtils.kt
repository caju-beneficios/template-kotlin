package br.com.caju.eventproducer.shared

import br.com.caju.domain.shared.config.Metadata.CORRELATION_ID
import br.com.caju.domain.shared.config.Metadata.REQUESTER_ID
import br.com.caju.domain.shared.config.correlationId
import java.nio.ByteBuffer
import kotlin.Int.Companion.SIZE_BYTES
import kotlinx.coroutines.future.await
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.header.internals.RecordHeader
import org.apache.kafka.common.header.internals.RecordHeaders
import org.springframework.kafka.core.KafkaTemplate

suspend fun <K, V> KafkaTemplate<K, V>.sendCorrelated(
    topic: String,
    key: K,
    value: V,
    headers: Map<String, Any>? = null,
) {
    val messageHeaders =
        RecordHeaders()
            .add(RecordHeader(CORRELATION_ID, correlationId().encodeToByteArray()))
            .add(RecordHeader(REQUESTER_ID, correlationId().encodeToByteArray()))
            .apply {
                headers?.iterator()?.forEach { add(RecordHeader(it.key, it.value.encodeToBytes())) }
            }

    val record = ProducerRecord(topic, null, key, value, messageHeaders)
    send(record).await()
}

inline fun <reified T : Any> T.encodeToBytes(): ByteArray =
    when (T::class) {
        String::class -> (this as String).encodeToByteArray()
        Long::class -> ByteBuffer.allocate(Long.SIZE_BYTES).putLong(this as Long).array()
        Int::class -> ByteBuffer.allocate(SIZE_BYTES).putInt(this as Int).array()
        Double::class -> ByteBuffer.allocate(Double.SIZE_BYTES).putDouble(this as Double).array()
        else -> this.toString().encodeToByteArray()
    }
