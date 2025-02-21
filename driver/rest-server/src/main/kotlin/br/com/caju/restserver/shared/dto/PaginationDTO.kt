package br.com.caju.restserver.shared.dto

import br.com.caju.domain.shared.pagination.model.Pagination
import br.com.caju.domain.shared.pagination.model.PaginationOrder
import br.com.caju.domain.shared.pagination.model.of
import com.fasterxml.jackson.annotation.JsonValue
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.bind.annotation.RequestParam

enum class PaginationOrderDTO(@JsonValue val value: String) {
    ASC("Asc"),
    DESC("Desc"),
}

class PaginationDTO(
    @Schema(name = "Page", description = "Current page") @RequestParam val page: Int?,
    @Schema(name = "PerPage", description = "Registers per page") @RequestParam val perPage: Int?,
    @Schema(name = "OrderBy", description = "Field to order by") @RequestParam val orderBy: String?,
    @Schema(name = "Order", description = "Order of the registers") val order: PaginationOrderDTO?,
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
