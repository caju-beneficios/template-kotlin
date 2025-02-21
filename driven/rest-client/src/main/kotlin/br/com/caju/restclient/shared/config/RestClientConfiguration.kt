package br.com.caju.restclient.shared.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
class RestClientConfiguration {

    @Bean fun webClient(): WebClient = WebClient.builder().build()

    @Bean
    fun webClientAdapter(webClient: WebClient): WebClientAdapter =
        WebClientAdapter.create(webClient)

    @Bean
    fun httpServiceProxyFactory(
        webClientAdapter: WebClientAdapter,
        environment: Environment,
    ): HttpServiceProxyFactory =
        HttpServiceProxyFactory.builderFor(webClientAdapter)
            .embeddedValueResolver(environment::resolvePlaceholders)
            .build()
}
