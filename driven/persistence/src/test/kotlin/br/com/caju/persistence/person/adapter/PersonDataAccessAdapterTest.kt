package br.com.caju.persistence.person.adapter

import br.com.caju.domain.person.model.Person
import br.com.caju.domain.shared.pagination.model.Pagination.OffsetPagination
import br.com.caju.domain.shared.pagination.model.PaginationOrder.ASC
import br.com.caju.domain.shared.utils.random
import br.com.caju.persistence.shared.config.PersistenceTestCase
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe

class PersonDataAccessAdapterTest(private val personDataAccessAdapter: PersonDataAccessAdapter) :
    PersistenceTestCase({
        fun anyPerson() = random<Person>()

        context("PersonDataAccessAdapterTest") {
            test("should create entity when not exists") {
                val person = anyPerson()

                personDataAccessAdapter.save(person) shouldBe person
            }
            test("should update entity when exists") {
                val person = anyPerson()

                personDataAccessAdapter.save(person) shouldBe person

                val updatedPerson = person.copy(name = random())

                personDataAccessAdapter.update(updatedPerson) shouldBe updatedPerson
            }
            test("should find entity by id") {
                val person = anyPerson()

                personDataAccessAdapter.save(person) shouldBe person

                personDataAccessAdapter.findById(person.id) shouldBe person
            }
            test("should find all entities") {
                val person1 = anyPerson()
                val person2 = anyPerson()

                personDataAccessAdapter.save(person1)
                personDataAccessAdapter.save(person2)

                val pagination = OffsetPagination(1, 10, ASC, null)

                personDataAccessAdapter.findAll(pagination).page shouldContainAll
                    listOf(person1, person2)
            }
            test("should find entity by cpf") {
                val person = anyPerson()

                personDataAccessAdapter.save(person) shouldBe person

                personDataAccessAdapter.findByCpf(person.cpf) shouldBe person
            }
            test("should check if entity exists by cpf") {
                val person = anyPerson()

                personDataAccessAdapter.save(person) shouldBe person

                personDataAccessAdapter.existsByCpf(person.cpf) shouldBe true
            }
        }
    })
