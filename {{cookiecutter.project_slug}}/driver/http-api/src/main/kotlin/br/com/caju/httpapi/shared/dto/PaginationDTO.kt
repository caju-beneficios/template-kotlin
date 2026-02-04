package br.com.caju.api.shared.dto

import br.com.caju.domain.shared.pagination.model.Pagination
import br.com.caju.domain.shared.pagination.model.PaginationOrder
import br.com.caju.domain.shared.pagination.model.of
import com.fasterxml.jackson.annotation.JsonValue
import io.swagger.v3.oas.annotations.media.Schema

enum class PaginationOrderDTO(@JsonValue val value: String) {
    ASC("Asc"),
    DESC("Desc"),
}

class PaginationDTO(
    @Schema(name = "page", description = "Current page") val page: Int?,
    @Schema(name = "perPage", description = "Registers per page") val perPage: Int?,
    @Schema(name = "orderBy", description = "Field to order by") val orderBy: String?,
    @Schema(name = "order", description = "Order of the registers") val order: PaginationOrderDTO?,
)

fun PaginationDTO.toOffsetModel() =
    Pagination.OffsetPagination.of(
        page,
        perPage,
        order?.let { PaginationOrder.valueOf(it.name) },
        orderBy,
    )

fun PaginationDTO.toCursorModel() =
    Pagination.CursorPagination.of(
        page,
        perPage,
        order?.let { PaginationOrder.valueOf(it.name) },
        orderBy,
    )