package br.com.caju.postgresqlpersistence.{{cookiecutter.resource_name_lower}}.repository

import br.com.caju.postgresqlpersistence.{{cookiecutter.resource_name_lower}}.entity.{{cookiecutter.resource_name}}Entity
import br.com.caju.postgresqlpersistence.shared.jpa.Repository.ReadRepository
import br.com.caju.postgresqlpersistence.shared.jpa.Repository.WriteRepository
import java.util.UUID
import org.springframework.stereotype.Repository

@Repository
interface {{cookiecutter.resource_name}}ReadRepository : ReadRepository<{{cookiecutter.resource_name}}Entity, UUID> {{ "{" }}{% if cookiecutter.resource_name_lower == 'article' %}
    fun findByTitle(title: String): {{cookiecutter.resource_name}}Entity?

    fun existsByTitle(title: String): Boolean
{% else %}    // TODO: Add custom read methods here
{% endif %}{{ "}" }}

@Repository interface {{cookiecutter.resource_name}}WriteRepository : WriteRepository<{{cookiecutter.resource_name}}Entity, UUID> {{ "{" }}{{ "}" }}