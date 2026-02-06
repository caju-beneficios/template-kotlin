package br.com.caju.postgresqlpersistence.{{cookiecutter._resource_name_package}}.repository

import br.com.caju.postgresqlpersistence.{{cookiecutter._resource_name_package}}.entity.{{cookiecutter._resource_name_class}}Entity
import br.com.caju.postgresqlpersistence.shared.jpa.Repository.ReadRepository
import br.com.caju.postgresqlpersistence.shared.jpa.Repository.WriteRepository
import java.util.UUID
import org.springframework.stereotype.Repository

@Repository
interface {{cookiecutter._resource_name_class}}ReadRepository : ReadRepository<{{cookiecutter._resource_name_class}}Entity, UUID> {{ "{" }}{% if cookiecutter._resource_name_package == 'article' %}
    fun findByTitle(title: String): {{cookiecutter._resource_name_class}}Entity?

    fun existsByTitle(title: String): Boolean
{% else %}    // TODO: Add custom read methods here
{% endif %}{{ "}" }}

@Repository interface {{cookiecutter._resource_name_class}}WriteRepository : WriteRepository<{{cookiecutter._resource_name_class}}Entity, UUID> {{ "{" }}{{ "}" }}