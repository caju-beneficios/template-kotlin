package br.com.caju.shared.dto

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import io.swagger.v3.oas.annotations.media.Schema

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes(
    JsonSubTypes.Type(value = OffsetMetaDTO::class, name = "OffsetMetaDTO"),
    JsonSubTypes.Type(value = CursorMetaDTO::class, name = "CursorMetaDTO"),
)
@Schema(
    name = "MetaDTO",
    description = "Pagination information",
    oneOf = [OffsetMetaDTO::class, CursorMetaDTO::class],
)
sealed interface MetaDTO

@Schema(name = "CursorMetaDTO", description = "Pagination information for Cursor pagination")
data class CursorMetaDTO(
    @Schema(
        name = "HasNext",
        description = "Boolean that indicates if there is more registers after this one",
    )
    @JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "hasNext",
    )
    val hasNext: Boolean
) : MetaDTO

@Schema(name = "OffsetMetaDTO", description = "Pagination information for Offset pagination")
data class OffsetMetaDTO(
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "page")
    val page: Int,
    val perPage: Int,
    val totalPages: Int,
    val totalItems: Long,
) : MetaDTO