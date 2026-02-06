package br.com.caju.domain.{{cookiecutter.resource_name_lower}}.usecase

{% if cookiecutter.include_pagination == 'y' %}import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model.{{cookiecutter.resource_name_class}}
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.port.driven.{{cookiecutter.resource_name_class}}DataAccessPort
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.port.driver.GetAll{{cookiecutter.resource_name_class}}sPaginatedPort
import br.com.caju.domain.shared.pagination.model.PaginatedModel
import br.com.caju.domain.shared.pagination.model.Pagination
import org.springframework.stereotype.Service

@Service
class GetAll{{cookiecutter.resource_name_class}}sPaginatedUseCase(private val {{cookiecutter.resource_name_lower}}DataAccessPort: {{cookiecutter.resource_name_class}}DataAccessPort) :
    GetAll{{cookiecutter.resource_name_class}}sPaginatedPort {
    override suspend fun getAll(pagination: Pagination): PaginatedModel<{{cookiecutter.resource_name_class}}> =
        {{cookiecutter.resource_name_lower}}DataAccessPort.findAll(pagination)
}
{% else %}
// Pagination is disabled for this resource
{% endif %}