package br.com.caju.postgresqlpersistence.{{cookiecutter.resource_name_lower}}.adapter

import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model.{{cookiecutter.resource_name}}{% if cookiecutter.include_pagination == "y" %}
import br.com.caju.domain.shared.pagination.model.Pagination.OffsetPagination
import br.com.caju.domain.shared.pagination.model.PaginationOrder.ASC{% endif %}
import br.com.caju.domain.shared.utils.random
import br.com.caju.postgresqlpersistence.shared.config.PersistenceTestCase{% if cookiecutter.include_pagination == "y" %}
import io.kotest.matchers.collections.shouldContainAll{% endif %}
import io.kotest.matchers.shouldBe

class {{cookiecutter.resource_name}}DataAccessAdapterTest(private val {{cookiecutter.resource_name_camel}}DataAccessAdapter: {{cookiecutter.resource_name}}DataAccessAdapter) :
    PersistenceTestCase({
        fun any{{cookiecutter.resource_name}}() = random<{{cookiecutter.resource_name}}>()

        context("{{cookiecutter.resource_name}}DataAccessAdapterTest") {
            test("should create entity when not exists") {
                val {{cookiecutter.resource_name_camel}} = any{{cookiecutter.resource_name}}()

                {{cookiecutter.resource_name_camel}}DataAccessAdapter.save({{cookiecutter.resource_name_camel}}) shouldBe {{cookiecutter.resource_name_camel}}
            }
            test("should update entity when exists") {
                val {{cookiecutter.resource_name_camel}} = any{{cookiecutter.resource_name}}()

                {{cookiecutter.resource_name_camel}}DataAccessAdapter.save({{cookiecutter.resource_name_camel}}) shouldBe {{cookiecutter.resource_name_camel}}

                val updated{{cookiecutter.resource_name}} = {{cookiecutter.resource_name_camel}}.copy({% if cookiecutter.resource_name_lower == 'article' %}title = random(){% else %}/* TODO: Update appropriate field */{% endif %})

                {{cookiecutter.resource_name_camel}}DataAccessAdapter.update(updated{{cookiecutter.resource_name}}) shouldBe updated{{cookiecutter.resource_name}}
            }
            test("should find entity by id") {
                val {{cookiecutter.resource_name_camel}} = any{{cookiecutter.resource_name}}()

                {{cookiecutter.resource_name_camel}}DataAccessAdapter.save({{cookiecutter.resource_name_camel}}) shouldBe {{cookiecutter.resource_name_camel}}

                {{cookiecutter.resource_name_camel}}DataAccessAdapter.findById({{cookiecutter.resource_name_camel}}.id) shouldBe {{cookiecutter.resource_name_camel}}
            }{% if cookiecutter.include_pagination == "y" %}
            test("should find all entities") {
                val {{cookiecutter.resource_name_camel}}1 = any{{cookiecutter.resource_name}}()
                val {{cookiecutter.resource_name_camel}}2 = any{{cookiecutter.resource_name}}()

                {{cookiecutter.resource_name_camel}}DataAccessAdapter.save({{cookiecutter.resource_name_camel}}1)
                {{cookiecutter.resource_name_camel}}DataAccessAdapter.save({{cookiecutter.resource_name_camel}}2)

                val pagination = OffsetPagination(1, 10, ASC, null)

                {{cookiecutter.resource_name_camel}}DataAccessAdapter.findAll(pagination).page shouldContainAll
                    listOf({{cookiecutter.resource_name_camel}}1, {{cookiecutter.resource_name_camel}}2)
            }{% endif %}{% if cookiecutter.resource_name_lower == 'article' %}
            test("should find entity by title") {
                val {{cookiecutter.resource_name_camel}} = any{{cookiecutter.resource_name}}()

                {{cookiecutter.resource_name_camel}}DataAccessAdapter.save({{cookiecutter.resource_name_camel}}) shouldBe {{cookiecutter.resource_name_camel}}

                {{cookiecutter.resource_name_camel}}DataAccessAdapter.findByTitle({{cookiecutter.resource_name_camel}}.title) shouldBe {{cookiecutter.resource_name_camel}}
            }
            test("should check if entity exists by title") {
                val {{cookiecutter.resource_name_camel}} = any{{cookiecutter.resource_name}}()

                {{cookiecutter.resource_name_camel}}DataAccessAdapter.save({{cookiecutter.resource_name_camel}}) shouldBe {{cookiecutter.resource_name_camel}}

                {{cookiecutter.resource_name_camel}}DataAccessAdapter.existsByTitle({{cookiecutter.resource_name_camel}}.title) shouldBe true
            }
{% endif %}        }
    })