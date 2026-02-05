package br.com.caju.postgresqlpersistence.shared.config

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@ComponentScan("br.com.caju.postgresqlpersistence")
@EntityScan("br.com.caju.postgresqlpersistence")
@EnableJpaRepositories("br.com.caju.postgresqlpersistence")
@EnableTransactionManagement
@SpringBootApplication
class PersistenceTestApplication
