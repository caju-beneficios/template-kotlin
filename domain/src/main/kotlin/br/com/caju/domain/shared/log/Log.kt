package br.com.caju.domain.shared.log

import br.com.caju.domain.shared.exception.AppException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC

inline fun <reified T> T.logger(): Logger = LoggerFactory.getLogger(T::class.java)

fun Logger.annotate(variable: MdcVar, value: Any) = apply {
    MDC.put(variable.name.lowercase(), value.toString())
}

fun Logger.annotate(variables: Map<MdcVar, Any>) = apply {
    variables.forEach { MDC.put(it.key.name.lowercase(), it.value.toString()) }
}

fun Throwable.applyMDC() = apply {
    when (this) {
        is AppException -> {
            MDC.put(::errorKey.name, errorKey)
            data.forEach { (key, value) -> MDC.put(key, value.toString()) }
        }
        else -> MDC.put("errorKey", "UNHANDLED_ERROR")
    }
}
