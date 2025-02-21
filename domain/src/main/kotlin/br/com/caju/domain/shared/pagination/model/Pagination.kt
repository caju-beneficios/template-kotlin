package br.com.caju.domain.shared.pagination.model

import br.com.caju.domain.shared.pagination.model.Pagination.CursorPagination
import br.com.caju.domain.shared.pagination.model.Pagination.DEFAULTS.DEFAULT_ORDER
import br.com.caju.domain.shared.pagination.model.Pagination.DEFAULTS.DEFAULT_ORDER_BY
import br.com.caju.domain.shared.pagination.model.Pagination.DEFAULTS.DEFAULT_PAGE
import br.com.caju.domain.shared.pagination.model.Pagination.DEFAULTS.DEFAULT_PAGE_SIZE
import br.com.caju.domain.shared.pagination.model.Pagination.DEFAULTS.MAX_PAGE_SIZE
import br.com.caju.domain.shared.pagination.model.Pagination.OffsetPagination
import kotlin.math.min

sealed interface Pagination {

    val page: Int
    val pageSize: Int
    val order: PaginationOrder
    val orderBy: String?

    data class OffsetPagination(
        override val page: Int,
        override val pageSize: Int,
        override val order: PaginationOrder,
        override val orderBy: String?,
    ) : Pagination {
        companion object {}
    }

    data class CursorPagination(
        override val page: Int,
        override val pageSize: Int,
        override val order: PaginationOrder,
        override val orderBy: String?,
    ) : Pagination {
        companion object {}
    }

    object DEFAULTS {
        /**
         * To follow `caju-api` standards, we need to consider the first page as 1 instead of 0
         *
         * For more info on the impacts of this decision, check the comment on the PageableBuilder
         * object definition
         */
        const val DEFAULT_PAGE = 1
        val DEFAULT_ORDER = PaginationOrder.DESC
        private const val MIN_PAGE_SIZE = 50
        const val MAX_PAGE_SIZE = 500
        const val DEFAULT_PAGE_SIZE = MIN_PAGE_SIZE
        const val DEFAULT_ORDER_BY = "createdAt"

        val default_pagination = OffsetPagination.of(null, null, null, null)
    }
}

fun CursorPagination.Companion.of(
    maybePage: Int?,
    maybePerPage: Int?,
    maybeOrder: PaginationOrder?,
    maybeOrderBy: String?,
): CursorPagination {
    val page = maybePage ?: DEFAULT_PAGE
    val perPage = min(maybePerPage ?: DEFAULT_PAGE_SIZE, MAX_PAGE_SIZE)
    val order = maybeOrder ?: DEFAULT_ORDER
    val orderBy = maybeOrderBy ?: DEFAULT_ORDER_BY

    return CursorPagination(page, perPage, order, orderBy)
}

fun OffsetPagination.Companion.of(
    maybePage: Int?,
    maybePerPage: Int?,
    maybeOrder: PaginationOrder? = null,
    maybeOrderBy: String? = null,
): OffsetPagination {
    val page = maybePage ?: DEFAULT_PAGE
    val perPage = min(maybePerPage ?: DEFAULT_PAGE_SIZE, MAX_PAGE_SIZE)
    val order = maybeOrder ?: DEFAULT_ORDER
    val orderBy = maybeOrderBy ?: DEFAULT_ORDER_BY

    return OffsetPagination(page, perPage, order, orderBy)
}
