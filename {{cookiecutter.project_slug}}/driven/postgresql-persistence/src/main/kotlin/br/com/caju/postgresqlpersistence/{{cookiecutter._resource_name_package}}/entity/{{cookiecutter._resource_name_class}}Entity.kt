package br.com.caju.postgresqlpersistence.{{cookiecutter._resource_name_package}}.entity

import br.com.caju.domain.{{cookiecutter._resource_name_package}}.model.{{cookiecutter._resource_name_class}}
import br.com.caju.postgresqlpersistence.shared.jpa.AuditableFull
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Version
import java.util.UUID
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes.VARCHAR

@Entity(name = "{{cookiecutter._resource_name_plural_package}}")
@Table
data class {{cookiecutter._resource_name_class}}Entity(
    @Id @JdbcTypeCode(VARCHAR) val id: UUID,
    @Version var version: Long? = null,{% if cookiecutter._resource_name_package == 'article' %}
    val title: String,
    val content: String,
    val status: String,
{% else %}    // TODO: Add entity fields here
{% endif %}) : AuditableFull()

fun {{cookiecutter._resource_name_class}}Entity.merge({{cookiecutter._resource_name_class}}: {{cookiecutter._resource_name_class}}) ={% if cookiecutter._resource_name_package == 'article' %}
    copy(title = {{cookiecutter._resource_name_class}}.title, content = {{cookiecutter._resource_name_class}}.content, status = {{cookiecutter._resource_name_class}}.status)
{% else %}    // TODO: Implement merge logic here
    this
{% endif %}

fun {{cookiecutter._resource_name_class}}Entity.toModel() = {{cookiecutter._resource_name_class}}(id = id{% if cookiecutter._resource_name_package == 'article' %}, title = title, content = content, status = status{% else %} /* TODO: Map entity fields to model */{% endif %})

fun {{cookiecutter._resource_name_class}}.toEntity() = {{cookiecutter._resource_name_class}}Entity(id = id{% if cookiecutter._resource_name_package == 'article' %}, title = title, content = content, status = status{% else %} /* TODO: Map model fields to entity */{% endif %})