package br.com.caju.domain.shared.transaction.port.driven

interface TransactionPort {
    suspend operator fun <T> invoke(block: suspend () -> T): T
}
