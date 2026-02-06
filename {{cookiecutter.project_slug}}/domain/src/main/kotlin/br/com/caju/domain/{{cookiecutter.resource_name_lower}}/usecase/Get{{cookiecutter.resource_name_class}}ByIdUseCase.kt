package br.com.caju.domain.{{cookiecutter.resource_name_lower}}.usecase

import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.exception.{{cookiecutter.resource_name_class}}NotExistsException
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model.{{cookiecutter.resource_name_class}}
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.port.driven.{{cookiecutter.resource_name_class}}DataAccessPort
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.port.driver.Get{{cookiecutter.resource_name_class}}ByIdPort
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class Get{{cookiecutter.resource_name_class}}ByIdUseCase(private val {{cookiecutter.resource_name_lower}}DataAccessPort: {{cookiecutter.resource_name_class}}DataAccessPort) :
    Get{{cookiecutter.resource_name_class}}ByIdPort {
    override suspend fun getById(id: UUID): {{cookiecutter.resource_name_class}} =
        {{cookiecutter.resource_name_lower}}DataAccessPort.findById(id) ?: throw {{cookiecutter.resource_name_class}}NotExistsException(id)
}