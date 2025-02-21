package br.com.caju.restclient.parking.config

import br.com.caju.restclient.parking.connector.PersonRestClientConnector
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import org.springframework.web.service.invoker.createClient

@Configuration
class PersonRestClientConfiguration {

    @Bean
    fun parkingRestClientConnector(
        httpServiceProxyFactory: HttpServiceProxyFactory
    ): PersonRestClientConnector = httpServiceProxyFactory.createClient<PersonRestClientConnector>()
}
