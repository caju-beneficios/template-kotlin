package br.com.caju.persistence.shared.config

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.listeners.BeforeProjectListener
import io.kotest.core.spec.IsolationMode.SingleInstance
import io.kotest.extensions.spring.SpringExtension
import java.time.ZoneOffset.UTC
import java.util.TimeZone

object PersistenceTestProjectConfiguration : AbstractProjectConfig() {

    override val isolationMode = SingleInstance
    override val parallelism: Int = 1

    object PersistenceTestExtension : BeforeProjectListener {
        override suspend fun beforeProject() = TimeZone.setDefault(TimeZone.getTimeZone(UTC))
    }

    override fun extensions() = listOf(SpringExtension, PersistenceTestExtension)
}
