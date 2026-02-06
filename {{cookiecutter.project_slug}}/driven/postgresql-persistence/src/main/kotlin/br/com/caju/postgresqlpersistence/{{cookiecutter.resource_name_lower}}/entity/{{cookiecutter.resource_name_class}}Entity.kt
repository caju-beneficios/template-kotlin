package br.com.caju.postgresqlpersistence.{{cookiecutter.resource_name_lower}}.entity

import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model.{{cookiecutter.resource_name_class}}
import br.com.caju.postgresqlpersistence.shared.jpa.AuditableFull
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Version
import java.util.UUID
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes.VARCHAR

@Entity(name = "{{cookiecutter.resource_name_lower}}s")
@Table
data class {{cookiecutter.resource_name_class}}Entity(
    @Id @JdbcTypeCode(VARCHAR) val id: UUID,
    @Version var version: Long? = null,
{% if cookiecutter.resource_name_lower == 'article' %}
    val title: String,
    val content: String,
    val status: String,
{% else %}    // TODO: Add entity fields here
{% endif %}) : AuditableFull()

fun {{cookiecutter.resource_name_class}}Entity.merge({{cookiecutter.resource_name_class}}: {{cookiecutter.resource_name_class}}) =
{% if cookiecutter.resource_name_lower == 'article' %}
    copy(title = {{cookiecutter.resource_name_class}}.title, content = {{cookiecutter.resource_name_class}}.content, status = {{cookiecutter.resource_name_class}}.status)
{% else %}
// TODO: Implement merge logic here
    this
{% endif %}

fun {{cookiecutter.resource_name_class}}Entity.toModel() = {{cookiecutter.resource_name_class}}(id = id{% if cookiecutter.resource_name_lower == 'article' %}, title = title, content = content, status = status{% else %} /* TODO: Map entity fields to model */{% endif %})

fun {{cookiecutter.resource_name_class}}.toEntity() = {{cookiecutter.resource_name_class}}Entity(id = id{% if cookiecutter.resource_name_lower == 'article' %}, title = title, content = content, status = status{% else %} /* TODO: Map model fields to entity */{% endif %})