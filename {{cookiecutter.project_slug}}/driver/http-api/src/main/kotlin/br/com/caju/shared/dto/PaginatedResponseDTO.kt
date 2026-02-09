package br.com.caju.shared.dto

import br.com.caju.domain.shared.pagination.model.PaginatedModel
import br.com.caju.domain.shared.pagination.model.Pagination
import br.com.caju.domain.shared.pagination.model.Pagination.CursorPagination
import br.com.caju.domain.shared.pagination.model.Pagination.OffsetPagination
import br.com.caju.shared.dto.PaginateResponse.paginate

data class PaginatedResponseDTO<T>(val items: List<T>, val meta: MetaDTO)

object PaginateResponse {
    inline fun <reified T : Any> List<T>.paginate(
        totalPages: Int,
        totalItems: Long,
        req: Pagination,
    ): PaginatedResponseDTO<T> {
        return when (req) {
            is OffsetPagination -> this.offsetPagination(totalPages, totalItems, req)
            is CursorPagination -> this.cursorPagination(totalItems, req)
        }
    }

    /**
     * This assumes that records are already paginated, i.e, items size are at most page size
     * `perPage` It calculates the total pages based on the total items.
     */
    inline fun <reified T : Any> List<T>.offsetPagination(
        totalPages: Int,
        totalItems: Long,
        req: OffsetPagination,
    ): PaginatedResponseDTO<T> =
        PaginatedResponseDTO(this, OffsetMetaDTO(req.page, req.pageSize, totalPages, totalItems))

    /**
     * Cursor pagination functions
     *
     * This assumes that records are already paginated, i.e, items size are at most page size
     * `perPage`. It calculates the total pages based on the total items.
     */
    inline fun <reified T : Any> List<T>.cursorPagination(
        totalItems: Long,
        req: CursorPagination,
    ): PaginatedResponseDTO<T> {
        val hasNext = totalItems > (req.page + req.pageSize)
        val meta = CursorMetaDTO(hasNext)

        return PaginatedResponseDTO(this, meta)
    }
}

inline fun <reified T : Any> PaginatedModel<T>.toResponseDTO(
    pagination: Pagination
): PaginatedResponseDTO<T> = page.paginate(totalPages, totalItems, pagination)

inline fun <From : Any, To : Any> PaginatedResponseDTO<From>.map(
    transform: (From) -> To
): PaginatedResponseDTO<To> {
    return PaginatedResponseDTO(items.map(transform), meta)
}

inline fun <From : Any, To : Any> PaginatedResponseDTO<From>.flatMap(
    transform: (From) -> List<To>
): PaginatedResponseDTO<To> {
    return PaginatedResponseDTO(items.flatMap(transform), meta)
}