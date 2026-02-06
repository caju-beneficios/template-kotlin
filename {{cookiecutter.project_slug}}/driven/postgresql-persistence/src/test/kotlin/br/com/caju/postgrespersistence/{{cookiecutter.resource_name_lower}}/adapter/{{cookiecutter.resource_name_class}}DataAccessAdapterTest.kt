package br.com.caju.postgresqlpersistence.{{cookiecutter.resource_name_lower}}.adapter

import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model.{{cookiecutter.resource_name_class}}
{% if cookiecutter.include_pagination == "y" %}
import br.com.caju.domain.shared.pagination.model.Pagination.OffsetPagination
import br.com.caju.domain.shared.pagination.model.PaginationOrder.ASC
{% endif %}
import br.com.caju.domain.shared.utils.random
import br.com.caju.postgresqlpersistence.shared.config.PersistenceTestCase
{% if cookiecutter.include_pagination == "y" %}
import io.kotest.matchers.collections.shouldContainAll
{% endif %}
import io.kotest.matchers.shouldBe

class {{cookiecutter.resource_name_class}}DataAccessAdapterTest(private val {{cookiecutter.resource_name_class}}DataAccessAdapter: {{cookiecutter.resource_name_class}}DataAccessAdapter) :
    PersistenceTestCase({
        fun any{{cookiecutter.resource_name_class}}() = random<{{cookiecutter.resource_name_class}}>()

        context("{{cookiecutter.resource_name_class}}DataAccessAdapterTest") {
            test("should create entity when not exists") {
                val {{cookiecutter.resource_name_class}} = any{{cookiecutter.resource_name_class}}()

                {{cookiecutter.resource_name_class}}DataAccessAdapter.save({{cookiecutter.resource_name_class}}) shouldBe {{cookiecutter.resource_name_class}}
            }
            test("should update entity when exists") {
                val {{cookiecutter.resource_name_class}} = any{{cookiecutter.resource_name_class}}()

                {{cookiecutter.resource_name_class}}DataAccessAdapter.save({{cookiecutter.resource_name_class}}) shouldBe {{cookiecutter.resource_name_class}}

                val updated{{cookiecutter.resource_name_class}} = {{cookiecutter.resource_name_class}}.copy(
                {% if cookiecutter.resource_name_lower == 'article' %}
                    title = random()
                {% else %}
                    /* TODO: Update appropriate field */
                {% endif %}
                )

                {{cookiecutter.resource_name_class}}DataAccessAdapter.update(updated{{cookiecutter.resource_name_class}}) shouldBe updated{{cookiecutter.resource_name_class}}
            }
            test("should find entity by id") {
                val {{cookiecutter.resource_name_class}} = any{{cookiecutter.resource_name_class}}()

                {{cookiecutter.resource_name_class}}DataAccessAdapter.save({{cookiecutter.resource_name_class}}) shouldBe {{cookiecutter.resource_name_class}}

                {{cookiecutter.resource_name_class}}DataAccessAdapter.findById({{cookiecutter.resource_name_class}}.id) shouldBe {{cookiecutter.resource_name_class}}
            }
            {% if cookiecutter.include_pagination == "y" %}
            test("should find all entities") {
                val {{cookiecutter.resource_name_class}}1 = any{{cookiecutter.resource_name_class}}()
                val {{cookiecutter.resource_name_class}}2 = any{{cookiecutter.resource_name_class}}()

                {{cookiecutter.resource_name_class}}DataAccessAdapter.save({{cookiecutter.resource_name_class}}1)
                {{cookiecutter.resource_name_class}}DataAccessAdapter.save({{cookiecutter.resource_name_class}}2)

                val pagination = OffsetPagination(1, 10, ASC, null)

                {{cookiecutter.resource_name_class}}DataAccessAdapter.findAll(pagination).page shouldContainAll
                    listOf({{cookiecutter.resource_name_class}}1, {{cookiecutter.resource_name_class}}2)
            }
            {% endif %}
            {% if cookiecutter.resource_name_lower == 'article' %}
            test("should find entity by title") {
                val {{cookiecutter.resource_name_class}} = any{{cookiecutter.resource_name_class}}()

                {{cookiecutter.resource_name_class}}DataAccessAdapter.save({{cookiecutter.resource_name_class}}) shouldBe {{cookiecutter.resource_name_class}}

                {{cookiecutter.resource_name_class}}DataAccessAdapter.findByTitle({{cookiecutter.resource_name_class}}.title) shouldBe {{cookiecutter.resource_name_class}}
            }
            test("should check if entity exists by title") {
                val {{cookiecutter.resource_name_class}} = any{{cookiecutter.resource_name_class}}()

                {{cookiecutter.resource_name_class}}DataAccessAdapter.save({{cookiecutter.resource_name_class}}) shouldBe {{cookiecutter.resource_name_class}}

                {{cookiecutter.resource_name_class}}DataAccessAdapter.existsByTitle({{cookiecutter.resource_name_class}}.title) shouldBe true
            }
{% endif %}
        }
    })