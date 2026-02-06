package br.com.caju.postgresqlpersistence.{{cookiecutter.resource_name_lower}}.adapter

import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model.{{cookiecutter.resource_name_class}}
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.port.driven.{{cookiecutter.resource_name_class}}DataAccessPort
{% if cookiecutter.include_pagination == "y" %}
import br.com.caju.domain.shared.pagination.model.PaginatedModel
import br.com.caju.domain.shared.pagination.model.Pagination
{% endif %}
import br.com.caju.postgresqlpersistence.{{cookiecutter.resource_name_lower}}.entity.merge
import br.com.caju.postgresqlpersistence.{{cookiecutter.resource_name_lower}}.entity.toEntity
import br.com.caju.postgresqlpersistence.{{cookiecutter.resource_name_lower}}.entity.toModel
import br.com.caju.postgresqlpersistence.{{cookiecutter.resource_name_lower}}.repository.{{cookiecutter.resource_name_class}}ReadRepository
import br.com.caju.postgresqlpersistence.{{cookiecutter.resource_name_lower}}.repository.{{cookiecutter.resource_name_class}}WriteRepository
import br.com.caju.postgresqlpersistence.shared.context.PersistenceContext
import br.com.caju.postgresqlpersistence.shared.entity.requiredEntity
{% if cookiecutter.include_pagination == "y" %}
import br.com.caju.postgresqlpersistence.shared.pagination.toPageable
import br.com.caju.postgresqlpersistence.shared.pagination.toPaginatedModel
{% endif %}
import datadog.trace.api.Trace
import java.util.UUID
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Service

@Service
class {{cookiecutter.resource_name_class}}DataAccessAdapter(
    private val {{cookiecutter.resource_name_class}}WriteRepository: {{cookiecutter.resource_name_class}}WriteRepository,
    private val {{cookiecutter.resource_name_class}}ReadRepository: {{cookiecutter.resource_name_class}}ReadRepository,
    private val context: PersistenceContext,
) : {{cookiecutter.resource_name_class}}DataAccessPort {

    @Trace
    override suspend fun save({{cookiecutter.resource_name_class}}: {{cookiecutter.resource_name_class}}): {{cookiecutter.resource_name_class}} = context {
        {{cookiecutter.resource_name_class}}WriteRepository.save({{cookiecutter.resource_name_class}}.toEntity()).toModel()
    }

    @Trace
    override suspend fun update({{cookiecutter.resource_name_class}}: {{cookiecutter.resource_name_class}}): {{cookiecutter.resource_name_class}} = context {
        {{cookiecutter.resource_name_class}}ReadRepository.findById({{cookiecutter.resource_name_class}}.id).getOrNull().requiredEntity({{cookiecutter.resource_name_class}}.id).run {
            {{cookiecutter.resource_name_class}}WriteRepository.save(merge({{cookiecutter.resource_name_class}})).toModel()
        }
    }
{% if cookiecutter.include_pagination == "y" %}

    @Trace
    override suspend fun findAll(pagination: Pagination): PaginatedModel<{{cookiecutter.resource_name_class}}> = context {
        {{cookiecutter.resource_name_class}}ReadRepository.findAll(pagination.toPageable()).toPaginatedModel { it.toModel() }
    }
{% endif %}
{% if cookiecutter.resource_name_lower == 'article' %}

    @Trace
    override suspend fun findByTitle(title: String): {{cookiecutter.resource_name_class}}? = context {
        {{cookiecutter.resource_name_class}}ReadRepository.findByTitle(title)?.toModel()
    }

    @Trace
    override suspend fun existsByTitle(title: String): Boolean = context {
        {{cookiecutter.resource_name_class}}ReadRepository.existsByTitle(title)
    }
{% endif %}

    @Trace
    override suspend fun findById(id: UUID): {{cookiecutter.resource_name_class}}? = context {
        {{cookiecutter.resource_name_class}}ReadRepository.findById(id).getOrNull()?.toModel()
    }
}