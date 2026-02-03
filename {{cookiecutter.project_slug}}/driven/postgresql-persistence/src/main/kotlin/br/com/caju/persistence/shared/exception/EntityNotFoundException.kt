package br.com.caju.persistence.shared.exception

import br.com.caju.domain.shared.exception.AppException.NotFoundException

data class EntityNotFoundException(
    override val message: String,
    override val data: Map<String, Any?>,
) : NotFoundException(message = message, errorKey = ERROR_KEY, expected = false, data = data) {
    companion object {
        const val ERROR_KEY = "ENTITY_NOT_FOUND"
    }
}
