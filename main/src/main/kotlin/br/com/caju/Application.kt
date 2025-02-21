package br.com.caju

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication @ConfigurationPropertiesScan("br.com.caju") class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
