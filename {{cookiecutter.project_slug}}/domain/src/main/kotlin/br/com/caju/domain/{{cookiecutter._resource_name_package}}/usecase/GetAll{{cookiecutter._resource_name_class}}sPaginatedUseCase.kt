package br.com.caju.domain.{{cookiecutter._resource_name_package}}.usecase

{% if cookiecutter.include_pagination == 'y' %}import br.com.caju.domain.{{cookiecutter._resource_name_package}}.model.{{cookiecutter._resource_name_class}}
import br.com.caju.domain.{{cookiecutter._resource_name_package}}.port.driven.{{cookiecutter._resource_name_class}}DataAccessPort
import br.com.caju.domain.{{cookiecutter._resource_name_package}}.port.driver.GetAll{{cookiecutter._resource_name_class}}sPaginatedPort
import br.com.caju.domain.shared.pagination.model.PaginatedModel
import br.com.caju.domain.shared.pagination.model.Pagination
import org.springframework.stereotype.Service

@Service
class GetAll{{cookiecutter._resource_name_class}}sPaginatedUseCase(private val {{cookiecutter._resource_name_package}}DataAccessPort: {{cookiecutter._resource_name_class}}DataAccessPort) :
    GetAll{{cookiecutter._resource_name_class}}sPaginatedPort {
    override suspend fun getAll(pagination: Pagination): PaginatedModel<{{cookiecutter._resource_name_class}}> =
        {{cookiecutter._resource_name_package}}DataAccessPort.findAll(pagination)
}
{% else %}
// Pagination is disabled for this resource
{% endif %}