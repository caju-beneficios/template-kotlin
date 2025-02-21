package br.com.caju.persistence.shared.config

import br.com.caju.persistence.shared.utils.DatabaseCleanUpService
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.FunSpec
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@Suppress("UNCHECKED_CAST")
@AutoConfigureTestDatabase(replace = NONE)
@AutoConfigureDataJpa
@Import(PersistenceTestConfiguration::class)
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [PersistenceTestApplication::class])
@SpringBootTest
abstract class PersistenceTestCase(body: PersistenceTestCase.() -> Unit = {}) :
    FunSpec(body as FunSpec.() -> Unit) {

    @Autowired protected lateinit var databaseCleanUpService: DatabaseCleanUpService

    override suspend fun beforeSpec(spec: Spec) = databaseCleanUpService()
}
