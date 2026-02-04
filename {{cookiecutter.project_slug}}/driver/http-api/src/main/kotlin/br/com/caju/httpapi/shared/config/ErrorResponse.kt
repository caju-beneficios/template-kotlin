package br.com.caju.api.shared.config

import br.com.caju.domain.shared.exception.AppException
import org.springframework.web.context.request.ServletWebRequest

data class ErrorResponse(val message: String, val errorKey: String, val data: Map<String, Any?>)

fun AppException.toErrorResponse() =
    ErrorResponse(message = message, errorKey = errorKey, data = data)

fun Throwable.toErrorResponse(request: ServletWebRequest) =
    ErrorResponse(
        message = "An unexpected error occurred while processing request",
        errorKey = "UNHANDLED_ERROR",
        data =
            mapOf(
                "method" to request.httpMethod,
                "path" to request.contextPath,
                "params" to request.parameterMap,
                "errorMessage" to (message ?: this::class.java),
            ),
    )