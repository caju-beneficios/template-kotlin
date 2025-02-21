package br.com.caju.shared.config

import br.com.caju.Application
import org.springframework.boot.fromApplication
import org.springframework.boot.with

fun main(args: Array<String>) {
    fromApplication<Application>().with(IntegrationTestConfiguration::class).run(*args)
}
