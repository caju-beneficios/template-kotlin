package br.com.caju.postgresqlpersistence.{{cookiecutter._resource_name_package}}.adapter

import br.com.caju.domain.{{cookiecutter._resource_name_package}}.model.{{cookiecutter._resource_name_class}}{% if cookiecutter.include_pagination == "y" %}
import br.com.caju.domain.shared.pagination.model.Pagination.OffsetPagination
import br.com.caju.domain.shared.pagination.model.PaginationOrder.ASC{% endif %}
import br.com.caju.domain.shared.utils.random
import br.com.caju.postgresqlpersistence.shared.config.PersistenceTestCase{% if cookiecutter.include_pagination == "y" %}
import io.kotest.matchers.collections.shouldContainAll{% endif %}
import io.kotest.matchers.shouldBe

class {{cookiecutter._resource_name_class}}DataAccessAdapterTest(private val {{cookiecutter._resource_name_class}}DataAccessAdapter: {{cookiecutter._resource_name_class}}DataAccessAdapter) :
    PersistenceTestCase({
        fun any{{cookiecutter._resource_name_class}}() = random<{{cookiecutter._resource_name_class}}>()

        context("{{cookiecutter._resource_name_class}}DataAccessAdapterTest") {
            test("should create entity when not exists") {
                val {{cookiecutter._resource_name_class}} = any{{cookiecutter._resource_name_class}}()

                {{cookiecutter._resource_name_class}}DataAccessAdapter.save({{cookiecutter._resource_name_class}}) shouldBe {{cookiecutter._resource_name_class}}
            }
            test("should update entity when exists") {
                val {{cookiecutter._resource_name_class}} = any{{cookiecutter._resource_name_class}}()

                {{cookiecutter._resource_name_class}}DataAccessAdapter.save({{cookiecutter._resource_name_class}}) shouldBe {{cookiecutter._resource_name_class}}

                val updated{{cookiecutter._resource_name_class}} = {{cookiecutter._resource_name_class}}.copy({% if cookiecutter._resource_name_package == 'article' %}title = random(){% else %}/* TODO: Update appropriate field */{% endif %})

                {{cookiecutter._resource_name_class}}DataAccessAdapter.update(updated{{cookiecutter._resource_name_class}}) shouldBe updated{{cookiecutter._resource_name_class}}
            }
            test("should find entity by id") {
                val {{cookiecutter._resource_name_class}} = any{{cookiecutter._resource_name_class}}()

                {{cookiecutter._resource_name_class}}DataAccessAdapter.save({{cookiecutter._resource_name_class}}) shouldBe {{cookiecutter._resource_name_class}}

                {{cookiecutter._resource_name_class}}DataAccessAdapter.findById({{cookiecutter._resource_name_class}}.id) shouldBe {{cookiecutter._resource_name_class}}
            }{% if cookiecutter.include_pagination == "y" %}
            test("should find all entities") {
                val {{cookiecutter._resource_name_class}}1 = any{{cookiecutter._resource_name_class}}()
                val {{cookiecutter._resource_name_class}}2 = any{{cookiecutter._resource_name_class}}()

                {{cookiecutter._resource_name_class}}DataAccessAdapter.save({{cookiecutter._resource_name_class}}1)
                {{cookiecutter._resource_name_class}}DataAccessAdapter.save({{cookiecutter._resource_name_class}}2)

                val pagination = OffsetPagination(1, 10, ASC, null)

                {{cookiecutter._resource_name_class}}DataAccessAdapter.findAll(pagination).page shouldContainAll
                    listOf({{cookiecutter._resource_name_class}}1, {{cookiecutter._resource_name_class}}2)
            }{% endif %}{% if cookiecutter._resource_name_package == 'article' %}
            test("should find entity by title") {
                val {{cookiecutter._resource_name_class}} = any{{cookiecutter._resource_name_class}}()

                {{cookiecutter._resource_name_class}}DataAccessAdapter.save({{cookiecutter._resource_name_class}}) shouldBe {{cookiecutter._resource_name_class}}

                {{cookiecutter._resource_name_class}}DataAccessAdapter.findByTitle({{cookiecutter._resource_name_class}}.title) shouldBe {{cookiecutter._resource_name_class}}
            }
            test("should check if entity exists by title") {
                val {{cookiecutter._resource_name_class}} = any{{cookiecutter._resource_name_class}}()

                {{cookiecutter._resource_name_class}}DataAccessAdapter.save({{cookiecutter._resource_name_class}}) shouldBe {{cookiecutter._resource_name_class}}

                {{cookiecutter._resource_name_class}}DataAccessAdapter.existsByTitle({{cookiecutter._resource_name_class}}.title) shouldBe true
            }
{% endif %}        }
    })