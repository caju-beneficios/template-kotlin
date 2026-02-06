package br.com.caju.domain.{{cookiecutter._resource_name_package}}.usecase

import br.com.caju.domain.{{cookiecutter._resource_name_package}}.exception.{{cookiecutter._resource_name_class}}NotExistsException
import br.com.caju.domain.{{cookiecutter._resource_name_package}}.model.{{cookiecutter._resource_name_class}}
import br.com.caju.domain.{{cookiecutter._resource_name_package}}.port.driven.{{cookiecutter._resource_name_class}}DataAccessPort
import br.com.caju.domain.{{cookiecutter._resource_name_package}}.port.driver.Get{{cookiecutter._resource_name_class}}ByIdPort
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class Get{{cookiecutter._resource_name_class}}ByIdUseCase(private val {{cookiecutter._resource_name_package}}DataAccessPort: {{cookiecutter._resource_name_class}}DataAccessPort) :
    Get{{cookiecutter._resource_name_class}}ByIdPort {
    override suspend fun getById(id: UUID): {{cookiecutter._resource_name_class}} =
        {{cookiecutter._resource_name_package}}DataAccessPort.findById(id) ?: throw {{cookiecutter._resource_name_class}}NotExistsException(id)
}