package br.com.caju.postgresqlpersistence.shared.transaction

import br.com.caju.domain.shared.transaction.port.driven.TransactionPort
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.transaction.support.TransactionTemplate.ISOLATION_READ_UNCOMMITTED

@Component
class TransactionAdapter(private val transactionTemplate: TransactionTemplate) : TransactionPort {

    override suspend fun <T> invoke(block: suspend () -> T): T =
        withContext(IO) {
            transactionTemplate.isolationLevel = ISOLATION_READ_UNCOMMITTED
            transactionTemplate.execute { runBlocking { block() } }!!
        }
}
