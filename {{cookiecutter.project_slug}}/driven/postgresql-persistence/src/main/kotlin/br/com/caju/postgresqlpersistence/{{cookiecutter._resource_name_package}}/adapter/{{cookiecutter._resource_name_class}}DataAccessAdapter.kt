package br.com.caju.postgresqlpersistence.{{cookiecutter._resource_name_package}}.adapter

import br.com.caju.domain.{{cookiecutter._resource_name_package}}.model.{{cookiecutter._resource_name_class}}
import br.com.caju.domain.{{cookiecutter._resource_name_package}}.port.driven.{{cookiecutter._resource_name_class}}DataAccessPort{% if cookiecutter.include_pagination == "y" %}
import br.com.caju.domain.shared.pagination.model.PaginatedModel
import br.com.caju.domain.shared.pagination.model.Pagination{% endif %}
import br.com.caju.postgresqlpersistence.{{cookiecutter._resource_name_package}}.entity.merge
import br.com.caju.postgresqlpersistence.{{cookiecutter._resource_name_package}}.entity.toEntity
import br.com.caju.postgresqlpersistence.{{cookiecutter._resource_name_package}}.entity.toModel
import br.com.caju.postgresqlpersistence.{{cookiecutter._resource_name_package}}.repository.{{cookiecutter._resource_name_class}}ReadRepository
import br.com.caju.postgresqlpersistence.{{cookiecutter._resource_name_package}}.repository.{{cookiecutter._resource_name_class}}WriteRepository
import br.com.caju.postgresqlpersistence.shared.context.PersistenceContext
import br.com.caju.postgresqlpersistence.shared.entity.requiredEntity{% if cookiecutter.include_pagination == "y" %}
import br.com.caju.postgresqlpersistence.shared.pagination.toPageable
import br.com.caju.postgresqlpersistence.shared.pagination.toPaginatedModel{% endif %}
import datadog.trace.api.Trace
import java.util.UUID
import kotlin.jvm.optionals.getOrNull
import org.springframework.stereotype.Service

@Service
class {{cookiecutter._resource_name_class}}DataAccessAdapter(
    private val {{cookiecutter._resource_name_class}}WriteRepository: {{cookiecutter._resource_name_class}}WriteRepository,
    private val {{cookiecutter._resource_name_class}}ReadRepository: {{cookiecutter._resource_name_class}}ReadRepository,
    private val context: PersistenceContext,
) : {{cookiecutter._resource_name_class}}DataAccessPort {

    @Trace
    override suspend fun save({{cookiecutter._resource_name_class}}: {{cookiecutter._resource_name_class}}): {{cookiecutter._resource_name_class}} = context {
        {{cookiecutter._resource_name_class}}WriteRepository.save({{cookiecutter._resource_name_class}}.toEntity()).toModel()
    }

    @Trace
    override suspend fun update({{cookiecutter._resource_name_class}}: {{cookiecutter._resource_name_class}}): {{cookiecutter._resource_name_class}} = context {
        {{cookiecutter._resource_name_class}}ReadRepository.findById({{cookiecutter._resource_name_class}}.id).getOrNull().requiredEntity({{cookiecutter._resource_name_class}}.id).run {
            {{cookiecutter._resource_name_class}}WriteRepository.save(merge({{cookiecutter._resource_name_class}})).toModel()
        }
    }
{% if cookiecutter.include_pagination == "y" %}
    @Trace
    override suspend fun findAll(pagination: Pagination): PaginatedModel<{{cookiecutter._resource_name_class}}> = context {
        {{cookiecutter._resource_name_class}}ReadRepository.findAll(pagination.toPageable()).toPaginatedModel { it.toModel() }
    }
{% endif %}{% if cookiecutter._resource_name_package == 'article' %}
    @Trace
    override suspend fun findByTitle(title: String): {{cookiecutter._resource_name_class}}? = context {
        {{cookiecutter._resource_name_class}}ReadRepository.findByTitle(title)?.toModel()
    }

    @Trace
    override suspend fun existsByTitle(title: String): Boolean = context {
        {{cookiecutter._resource_name_class}}ReadRepository.existsByTitle(title)
    }
{% endif %}
    @Trace
    override suspend fun findById(id: UUID): {{cookiecutter._resource_name_class}}? = context {
        {{cookiecutter._resource_name_class}}ReadRepository.findById(id).getOrNull()?.toModel()
    }
}