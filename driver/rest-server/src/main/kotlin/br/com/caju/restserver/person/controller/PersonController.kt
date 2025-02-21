package br.com.caju.restserver.person.controller

import br.com.caju.domain.person.port.driver.CreatePersonPort
import br.com.caju.domain.person.port.driver.GetPersonByIdPort
import br.com.caju.domain.person.port.driver.UpdatePersonPort
import br.com.caju.restserver.person.dto.PersonRequestDTO
import br.com.caju.restserver.person.dto.toDTO
import java.util.UUID
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/v1/person")
@RestController
class PersonController(
    private val createPersonPort: CreatePersonPort,
    private val updatePersonPort: UpdatePersonPort,
    private val getPersonByIdPort: GetPersonByIdPort,
) {
    @PostMapping()
    suspend fun save(@RequestBody personRequestDTO: PersonRequestDTO) =
        createPersonPort(personRequestDTO.toCreateCommand()).toDTO()

    @PutMapping("/{id}")
    suspend fun update(
        @RequestBody personRequestDTO: PersonRequestDTO,
        @PathVariable("id") id: UUID,
    ) = updatePersonPort(personRequestDTO.toUpdateCommand(id)).toDTO()

    @GetMapping("{id}")
    suspend fun getById(@PathVariable("id") id: UUID) = getPersonByIdPort.getById(id).toDTO()
}
