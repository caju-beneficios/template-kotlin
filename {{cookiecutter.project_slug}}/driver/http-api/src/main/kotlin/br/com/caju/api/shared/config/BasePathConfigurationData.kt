package br.com.caju.api.shared.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "server.base-path")
data class BasePathConfigurationData(
    val ignoredHandlers: List<String> = emptyList(),
    val prefix: String,
)