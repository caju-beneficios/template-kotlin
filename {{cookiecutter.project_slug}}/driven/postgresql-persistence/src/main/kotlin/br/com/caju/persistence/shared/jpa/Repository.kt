package br.com.caju.persistence.shared.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
sealed interface Repository<T : Any, ID : Any> : JpaRepository<T, ID> {
    interface WriteRepository<T : Any, ID : Any> : Repository<T, ID>

    interface ReadRepository<T : Any, ID : Any> : Repository<T, ID> {}
}
