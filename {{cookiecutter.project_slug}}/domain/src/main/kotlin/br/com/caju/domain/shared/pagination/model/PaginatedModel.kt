package br.com.caju.domain.shared.pagination.model

data class PaginatedModel<T>(val page: List<T>, val totalPages: Int, val totalItems: Long)
