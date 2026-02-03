package br.com.caju.api.shared.config

import br.com.caju.domain.shared.exception.AppException
import br.com.caju.domain.shared.exception.AppException.AlreadyExistsException
import br.com.caju.domain.shared.exception.AppException.BusinessException
import br.com.caju.domain.shared.exception.AppException.GeneralException
import br.com.caju.domain.shared.exception.AppException.NotFoundException
import br.com.caju.domain.shared.exception.AppException.TooManyRequestsClientException
import org.slf4j.MDC
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.TOO_MANY_REQUESTS
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalThrowableExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [Throwable::class])
    fun handleGenericError(exception: Throwable, request: ServletWebRequest): ResponseEntity<*> {
        return exception.toErrorResponse(request).let {
            logger.error(it.message, exception.applyMDC(request))
            ResponseEntity.status(INTERNAL_SERVER_ERROR).body(it)
        }
    }

    @ExceptionHandler(value = [AppException::class])
    fun handleBusinessError(
        exception: AppException,
        request: ServletWebRequest,
    ): ResponseEntity<*> {
        logger.error(exception.message, exception.applyMDC(request))

        return ResponseEntity.status(exception.httpStatus()).body(exception.toErrorResponse())
    }

    private fun AppException.httpStatus() =
        when (this) {
            is BusinessException -> BAD_REQUEST
            is NotFoundException -> NOT_FOUND
            is GeneralException -> INTERNAL_SERVER_ERROR
            is TooManyRequestsClientException -> TOO_MANY_REQUESTS
            is AlreadyExistsException -> CONFLICT
        }

    private fun AppException.applyMDC(request: ServletWebRequest) = apply {
        request.parameterMap.forEach { (key, value) -> MDC.put(key, value.joinToString()) }
        MDC.put(::errorKey.name, errorKey)
        data.forEach { (key, value) -> MDC.put(key, value.toString()) }
    }

    private fun Throwable.applyMDC(request: ServletWebRequest) = apply {
        MDC.put("errorKey", "UNHANDLED_ERROR")
        request.parameterMap.forEach { (key, value) -> MDC.put(key, value.joinToString()) }
    }
}