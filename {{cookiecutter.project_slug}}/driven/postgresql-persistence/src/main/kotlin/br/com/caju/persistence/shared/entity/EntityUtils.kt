package br.com.caju.persistence.shared.entity

import br.com.caju.persistence.shared.exception.EntityNotFoundException

inline fun <reified T> T?.requiredEntity(id: Any): T =
    this
        ?: throw EntityNotFoundException(
            message = T::class.simpleName?.replace("Entity", "") + " not found",
            data = mapOf("id" to id),
        )
