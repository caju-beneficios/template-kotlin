package br.com.caju.postgresqlpersistence.shared.context

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class PersistenceContext {

    suspend operator fun <T> invoke(block: () -> T): T = withContext(IO) { block() }
}
