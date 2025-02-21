package br.com.caju.domain.shared.exception

sealed class AppException(override val message: String, open val data: Map<String, Any?>) :
    RuntimeException(message) {
    abstract val errorKey: String
    abstract val expected: Boolean

    open class BusinessException(
        override val message: String,
        override val errorKey: String,
        override val expected: Boolean,
        override val data: Map<String, Any?> = emptyMap(),
    ) : AppException(message, data)

    open class NotFoundException(
        override val message: String,
        override val errorKey: String,
        override val expected: Boolean,
        override val data: Map<String, Any?> = emptyMap(),
    ) : AppException(message, data)

    open class AlreadyExistsException(
        override val message: String,
        override val errorKey: String,
        override val expected: Boolean,
        override val data: Map<String, Any?> = emptyMap(),
    ) : AppException(message, data)

    open class GeneralException(
        override val message: String,
        override val errorKey: String,
        override val data: Map<String, Any?> = emptyMap(),
    ) : AppException(message, data) {
        override val expected = false
    }

    open class TooManyRequestsClientException(
        override val message: String,
        override val data: Map<String, Any?> = emptyMap(),
    ) : AppException(message, data) {
        override val errorKey = ERROR_KEY
        override val expected = true

        companion object {
            const val ERROR_KEY = "TOO_MANY_REQUESTS_CLIENT_ERROR"
        }
    }
}
