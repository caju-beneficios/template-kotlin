package br.com.caju.persistence.shared.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
sealed interface Repository<T, ID> : JpaRepository<T, ID> {
    interface WriteRepository<T, ID> : Repository<T, ID>

    interface ReadRepository<T, ID> : Repository<T, ID> {}
}
