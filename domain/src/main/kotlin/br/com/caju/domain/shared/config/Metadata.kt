package br.com.caju.domain.shared.config

import br.com.caju.domain.shared.config.Metadata.CORRELATION_ID
import br.com.caju.domain.shared.config.Metadata.REQUESTER_ID
import java.util.UUID.randomUUID
import org.slf4j.MDC

object Metadata {
    const val CORRELATION_ID_HEADER = "X-Caju-Correlation-Id"
    const val REQUESTER_ID_HEADER = "requesterId"
    const val CORRELATION_ID = "correlationId"
    const val REQUESTER_ID = "requesterId"
}

fun correlationId() = MDC.get(CORRELATION_ID) ?: randomUUID().toString()

fun requesterId() = MDC.get(REQUESTER_ID) ?: randomUUID().toString()
