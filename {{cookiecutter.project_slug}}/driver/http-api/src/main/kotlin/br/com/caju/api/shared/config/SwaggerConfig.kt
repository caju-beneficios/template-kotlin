package br.com.caju.api.shared.config

import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun api(): GroupedOpenApi =
        GroupedOpenApi.builder()
            .group("{{cookiecutter.project_slug}}")
            .pathsToMatch("/**")
            .displayName("{{cookiecutter.project_name}}")
            .build()
}