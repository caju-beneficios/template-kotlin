package br.com.caju.persistence.shared.jpa

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration @EnableJpaAuditing @EnableTransactionManagement class JpaConfiguration
