package br.com.caju.persistence.shared.config

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@ComponentScan("br.com.caju.persistence")
@EntityScan("br.com.caju.persistence")
@EnableJpaRepositories("br.com.caju.persistence")
@EnableTransactionManagement
@SpringBootApplication
class PersistenceTestApplication
