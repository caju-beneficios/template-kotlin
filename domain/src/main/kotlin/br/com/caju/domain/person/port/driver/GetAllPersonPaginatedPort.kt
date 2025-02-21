package br.com.caju.domain.person.port.driver

import br.com.caju.domain.person.model.Person
import br.com.caju.domain.shared.pagination.model.PaginatedModel
import br.com.caju.domain.shared.pagination.model.Pagination

fun interface GetAllPersonPaginatedPort {
    suspend fun getAll(pagination: Pagination): PaginatedModel<Person>
}
