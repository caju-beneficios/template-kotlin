package br.com.caju.postgresqlpersistence.shared.pagination

import br.com.caju.domain.shared.pagination.model.PaginatedModel
import br.com.caju.domain.shared.pagination.model.Pagination
import br.com.caju.domain.shared.pagination.model.Pagination.CursorPagination
import br.com.caju.domain.shared.pagination.model.Pagination.DEFAULTS.DEFAULT_ORDER
import br.com.caju.domain.shared.pagination.model.Pagination.DEFAULTS.DEFAULT_ORDER_BY
import br.com.caju.domain.shared.pagination.model.Pagination.DEFAULTS.DEFAULT_PAGE
import br.com.caju.domain.shared.pagination.model.Pagination.DEFAULTS.DEFAULT_PAGE_SIZE
import br.com.caju.domain.shared.pagination.model.Pagination.OffsetPagination
import br.com.caju.domain.shared.pagination.model.PaginationOrder
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Order

fun Pagination.toPageable(): Pageable =
    when (this) {
        is CursorPagination -> toPageablePattern()
        is OffsetPagination -> toPageablePattern()
        else ->
            PageRequest.of(
                DEFAULT_PAGE - 1,
                DEFAULT_PAGE_SIZE,
                Sort.by(
                    when (DEFAULT_ORDER) {
                        PaginationOrder.ASC -> Order.asc(DEFAULT_ORDER_BY)
                        PaginationOrder.DESC -> Order.desc(DEFAULT_ORDER_BY)
                    }
                ),
            )
    }

fun Pagination.all(): Pageable =
    when (this) {
        is CursorPagination -> toPageablePattern(0, Int.MAX_VALUE)
        is OffsetPagination -> toPageablePattern(0, Int.MAX_VALUE)
        else -> Pageable.unpaged()
    }

private fun Pagination.toPageablePattern(
    customPageNumber: Int = page - 1,
    customPageSize: Int = pageSize,
): Pageable =
    PageRequest.of(customPageNumber, customPageSize, Sort.by(orderByOrDefault(order, orderBy)))

fun <To : Any, From : Any> Page<To>.toPaginatedModel(mapper: (To) -> From) =
    PaginatedModel(
        page = content.map { mapper(it) },
        totalPages = totalPages,
        totalItems = totalElements,
    )

internal fun orderByOrDefault(order: PaginationOrder, orderBy: String?): Order =
    when (order) {
        PaginationOrder.ASC -> orderBy?.let { Order.asc(it) } ?: Order.asc(DEFAULT_ORDER_BY)
        PaginationOrder.DESC -> orderBy?.let { Order.desc(it) } ?: Order.desc(DEFAULT_ORDER_BY)
    }
