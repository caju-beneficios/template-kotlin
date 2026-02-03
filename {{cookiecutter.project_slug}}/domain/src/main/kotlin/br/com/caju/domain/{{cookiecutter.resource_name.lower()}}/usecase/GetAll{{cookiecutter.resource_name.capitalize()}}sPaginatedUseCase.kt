package br.com.caju.domain.{{cookiecutter.resource_name.lower()}}.usecase

{% if cookiecutter.include_pagination == 'y' %}import br.com.caju.domain.{{cookiecutter.resource_name.lower()}}.model.{{cookiecutter.resource_name.capitalize()}}
import br.com.caju.domain.{{cookiecutter.resource_name.lower()}}.port.driven.{{cookiecutter.resource_name.capitalize()}}DataAccessPort
import br.com.caju.domain.{{cookiecutter.resource_name.lower()}}.port.driver.GetAll{{cookiecutter.resource_name.capitalize()}}sPaginatedPort
import br.com.caju.domain.shared.pagination.model.PaginatedModel
import br.com.caju.domain.shared.pagination.model.Pagination
import org.springframework.stereotype.Service

@Service
class GetAll{{cookiecutter.resource_name.capitalize()}}sPaginatedUseCase(private val {{cookiecutter.resource_name.lower()}}DataAccessPort: {{cookiecutter.resource_name.capitalize()}}DataAccessPort) :
    GetAll{{cookiecutter.resource_name.capitalize()}}sPaginatedPort {
    override suspend fun getAll(pagination: Pagination): PaginatedModel<{{cookiecutter.resource_name.capitalize()}}> =
        {{cookiecutter.resource_name.lower()}}DataAccessPort.findAll(pagination)
}
{% else %}
// Pagination is disabled for this resource
{% endif %}