package br.com.caju

import org.springframework.boot.fromApplication
import org.springframework.boot.with

fun main(args: Array<String>) {
    fromApplication<QuickstartKotlinSpringApplication>()
        .with(TestcontainersConfiguration::class)
        .run(*args)
}
