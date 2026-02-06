package br.com.caju.{{cookiecutter.resource_name_lower}}.controller

import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.port.driver.Create{{cookiecutter.resource_name_class}}Port
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.port.driver.Get{{cookiecutter.resource_name_class}}ByIdPort
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.port.driver.Update{{cookiecutter.resource_name_class}}Port
{% if cookiecutter.include_pagination == 'y' %}
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.port.driver.GetAll{{cookiecutter.resource_name_class}}sPaginatedPort
import br.com.caju.shared.dto.PaginatedResponseDTO
import br.com.caju.shared.dto.PaginationDTO
import br.com.caju.shared.dto.PaginationOrderDTO
import br.com.caju.shared.dto.toOffsetModel
{% endif %}
{% if cookiecutter.resource_name == 'Article' %}
import br.com.caju.{{cookiecutter.resource_name_lower}}.dto.{{cookiecutter.resource_name_class}}RequestDTO
import br.com.caju.{{cookiecutter.resource_name_lower}}.dto.{{cookiecutter.resource_name_class}}ResponseDTO
import br.com.caju.{{cookiecutter.resource_name_lower}}.dto.toCreateCommand
import br.com.caju.{{cookiecutter.resource_name_lower}}.dto.toUpdateCommand
import br.com.caju.{{cookiecutter.resource_name_lower}}.dto.toDTO
{% if cookiecutter.include_pagination == 'y' %}
import br.com.caju.{{cookiecutter.resource_name_lower}}.dto.toResponseDTO
{% endif %}
{% else %}
// TODO: Create and import your DTOs here
// import br.com.caju.{{cookiecutter.resource_name_lower}}.dto.{{cookiecutter.resource_name_class}}RequestDTO
// import br.com.caju.{{cookiecutter.resource_name_lower}}.dto.{{cookiecutter.resource_name_class}}ResponseDTO
// import br.com.caju.{{cookiecutter.resource_name_lower}}.dto.toCreateCommand
// import br.com.caju.{{cookiecutter.resource_name_lower}}.dto.toUpdateCommand
// import br.com.caju.{{cookiecutter.resource_name_lower}}.dto.toDTO
{% if cookiecutter.include_pagination == 'y' %}
// import br.com.caju.{{cookiecutter.resource_name_lower}}.dto.toResponseDTO
{% endif %}
{% endif %}
import java.util.UUID
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
{% if cookiecutter.include_pagination == 'y' %}
import org.springframework.web.bind.annotation.RequestParam
{% endif %}
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/{{cookiecutter.rest_endpoint_version}}/{{cookiecutter.resource_name_lower}}s")
@RestController
class {{cookiecutter.resource_name_class}}sController(
    private val create{{cookiecutter.resource_name_class}}Port: Create{{cookiecutter.resource_name_class}}Port,
    private val update{{cookiecutter.resource_name_class}}Port: Update{{cookiecutter.resource_name_class}}Port,
    private val get{{cookiecutter.resource_name_class}}ByIdPort: Get{{cookiecutter.resource_name_class}}ByIdPort,
{% if cookiecutter.include_pagination == 'y' %}
    private val getAll{{cookiecutter.resource_name_class}}sPaginatedPort: GetAll{{cookiecutter.resource_name_class}}sPaginatedPort,
{% endif %}
) {
{% if cookiecutter.resource_name == 'Article' %}
    @PostMapping()
    suspend fun save(@RequestBody {{cookiecutter.resource_name_lower}}RequestDTO: {{cookiecutter.resource_name_class}}RequestDTO) =
        create{{cookiecutter.resource_name_class}}Port({{cookiecutter.resource_name_lower}}RequestDTO.toCreateCommand()).toDTO()

    @PutMapping("/{id}")
    suspend fun update(
        @RequestBody {{cookiecutter.resource_name_lower}}RequestDTO: {{cookiecutter.resource_name_class}}RequestDTO,
        @PathVariable("id") id: UUID,
    ) = update{{cookiecutter.resource_name_class}}Port({{cookiecutter.resource_name_lower}}RequestDTO.toUpdateCommand(id)).toDTO()
{% else %}

    // TODO: Implement your controller methods here
    // @PostMapping()
    // suspend fun save(@RequestBody {{cookiecutter.resource_name_lower}}RequestDTO: {{cookiecutter.resource_name_class}}RequestDTO) =
    //     create{{cookiecutter.resource_name_class}}Port({{cookiecutter.resource_name_lower}}RequestDTO.toCreateCommand()).toDTO()

    // @PutMapping("/{id}")
    // suspend fun update(@RequestBody {{cookiecutter.resource_name_lower}}RequestDTO: {{cookiecutter.resource_name_class}}RequestDTO, @PathVariable("id") id: UUID) =
    //     update{{cookiecutter.resource_name_class}}Port({{cookiecutter.resource_name_lower}}RequestDTO.toUpdateCommand(id)).toDTO(){% endif %}{% if cookiecutter.resource_name == 'Article' %}

    @GetMapping("{id}")
    suspend fun getById(@PathVariable("id") id: UUID) = get{{cookiecutter.resource_name_class}}ByIdPort.getById(id).toDTO()

{% else %}

    // TODO: Implement getById method here
    // @GetMapping("{id}")
    // suspend fun getById(@PathVariable("id") id: UUID) = get{{cookiecutter.resource_name_class}}ByIdPort.getById(id).toDTO()
{% endif %}
{% if cookiecutter.include_pagination == 'y' %}
    @GetMapping()
    suspend fun getAll(
        @RequestParam(value = "page", required = false) page: Int?,
        @RequestParam(value = "perPage", required = false) perPage: Int?,
        @RequestParam(value = "orderBy", required = false) orderBy: String?,
        @RequestParam(value = "order", required = false) order: PaginationOrderDTO?,
    ): PaginatedResponseDTO<{{cookiecutter.resource_name_class}}ResponseDTO> {
        val pagination = PaginationDTO(page, perPage, orderBy, order)
        val paginationModel = pagination.toOffsetModel()
        return getAll{{cookiecutter.resource_name_class}}sPaginatedPort.getAll(paginationModel)
            .toResponseDTO(paginationModel)
    }
{% endif %}
}