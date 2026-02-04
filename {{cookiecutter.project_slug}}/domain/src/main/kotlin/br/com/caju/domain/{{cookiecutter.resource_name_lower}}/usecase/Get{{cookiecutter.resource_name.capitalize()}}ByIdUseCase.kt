package br.com.caju.domain.{{cookiecutter.resource_name_lower}}.usecase

import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.exception.{{cookiecutter.resource_name.capitalize()}}NotExistsException
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model.{{cookiecutter.resource_name.capitalize()}}
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.port.driven.{{cookiecutter.resource_name.capitalize()}}DataAccessPort
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.port.driver.Get{{cookiecutter.resource_name.capitalize()}}ByIdPort
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class Get{{cookiecutter.resource_name.capitalize()}}ByIdUseCase(private val {{cookiecutter.resource_name_lower}}DataAccessPort: {{cookiecutter.resource_name.capitalize()}}DataAccessPort) :
    Get{{cookiecutter.resource_name.capitalize()}}ByIdPort {
    override suspend fun getById(id: UUID): {{cookiecutter.resource_name.capitalize()}} =
        {{cookiecutter.resource_name_lower}}DataAccessPort.findById(id) ?: throw {{cookiecutter.resource_name.capitalize()}}NotExistsException(id)
}