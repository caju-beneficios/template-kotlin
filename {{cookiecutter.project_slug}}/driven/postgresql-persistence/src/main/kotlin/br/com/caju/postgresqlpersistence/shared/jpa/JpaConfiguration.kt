package br.com.caju.postgresqlpersistence.shared.jpa

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration @EnableJpaAuditing @EnableTransactionManagement class JpaConfiguration
