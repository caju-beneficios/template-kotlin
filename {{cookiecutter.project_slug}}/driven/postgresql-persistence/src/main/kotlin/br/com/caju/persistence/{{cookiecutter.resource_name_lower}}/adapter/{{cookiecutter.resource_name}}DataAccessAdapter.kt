package br.com.caju.persistence.{{cookiecutter.resource_name_lower}}.adapter

import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model.{{cookiecutter.resource_name}}
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.port.driven.{{cookiecutter.resource_name}}DataAccessPort{% if cookiecutter.include_pagination == "y" %}
import br.com.caju.domain.shared.pagination.model.PaginatedModel
import br.com.caju.domain.shared.pagination.model.Pagination{% endif %}
import br.com.caju.persistence.{{cookiecutter.resource_name_lower}}.entity.merge
import br.com.caju.persistence.{{cookiecutter.resource_name_lower}}.entity.toEntity
import br.com.caju.persistence.{{cookiecutter.resource_name_lower}}.entity.toModel
import br.com.caju.persistence.{{cookiecutter.resource_name_lower}}.repository.{{cookiecutter.resource_name}}ReadRepository
import br.com.caju.persistence.{{cookiecutter.resource_name_lower}}.repository.{{cookiecutter.resource_name}}WriteRepository
import br.com.caju.persistence.shared.context.PersistenceContext
import br.com.caju.persistence.shared.entity.requiredEntity{% if cookiecutter.include_pagination == "y" %}
import br.com.caju.persistence.shared.pagination.toPageable
import br.com.caju.persistence.shared.pagination.toPaginatedModel{% endif %}
import datadog.trace.api.Trace
import java.util.UUID
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Service

@Service
class {{cookiecutter.resource_name}}DataAccessAdapter(
    private val {{cookiecutter.resource_name_camel}}WriteRepository: {{cookiecutter.resource_name}}WriteRepository,
    private val {{cookiecutter.resource_name_camel}}ReadRepository: {{cookiecutter.resource_name}}ReadRepository,
    private val context: PersistenceContext,
) : {{cookiecutter.resource_name}}DataAccessPort {

    @Trace
    override suspend fun save({{cookiecutter.resource_name_camel}}: {{cookiecutter.resource_name}}): {{cookiecutter.resource_name}} = context {
        {{cookiecutter.resource_name_camel}}WriteRepository.save({{cookiecutter.resource_name_camel}}.toEntity()).toModel()
    }

    @Trace
    override suspend fun update({{cookiecutter.resource_name_camel}}: {{cookiecutter.resource_name}}): {{cookiecutter.resource_name}} = context {
        {{cookiecutter.resource_name_camel}}ReadRepository.findById({{cookiecutter.resource_name_camel}}.id).getOrNull().requiredEntity({{cookiecutter.resource_name_camel}}.id).run {
            {{cookiecutter.resource_name_camel}}WriteRepository.save(merge({{cookiecutter.resource_name_camel}})).toModel()
        }
    }
{% if cookiecutter.include_pagination == "y" %}
    @Trace
    override suspend fun findAll(pagination: Pagination): PaginatedModel<{{cookiecutter.resource_name}}> = context {
        {{cookiecutter.resource_name_camel}}ReadRepository.findAll(pagination.toPageable()).toPaginatedModel { it.toModel() }
    }
{% endif %}{% if cookiecutter.resource_name_lower == 'article' %}
    @Trace
    override suspend fun findByTitle(title: String): {{cookiecutter.resource_name}}? = context {
        {{cookiecutter.resource_name_camel}}ReadRepository.findByTitle(title)?.toModel()
    }

    @Trace
    override suspend fun existsByTitle(title: String): Boolean = context {
        {{cookiecutter.resource_name_camel}}ReadRepository.existsByTitle(title)
    }
{% endif %}
    @Trace
    override suspend fun findById(id: UUID): {{cookiecutter.resource_name}}? = context {
        {{cookiecutter.resource_name_camel}}ReadRepository.findById(id).getOrNull()?.toModel()
    }
}