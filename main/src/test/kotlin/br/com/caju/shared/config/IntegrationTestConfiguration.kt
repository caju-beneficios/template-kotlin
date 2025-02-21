package br.com.caju.shared.config

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
class IntegrationTestConfiguration {

    @Bean
    @ServiceConnection("mysql")
    fun mysqlContainer(): MySQLContainer<*> =
        MySQLContainer(DockerImageName.parse(MYSQL_DOCKER_IMAGE))

    @Bean
    fun wiremockContainer(): WireMockServer =
        WireMockServer(WireMockConfiguration.options().port(WIREMOCK_DEFAULT_PORT)).also {
            it.start()
        }

    companion object {
        const val MYSQL_DOCKER_IMAGE = "mysql:8.4.4"
        const val WIREMOCK_DEFAULT_PORT = 9292
    }
}
