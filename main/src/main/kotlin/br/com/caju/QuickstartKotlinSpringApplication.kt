package br.com.caju

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication class QuickstartKotlinSpringApplication

fun main(args: Array<String>) {
    runApplication<QuickstartKotlinSpringApplication>(*args)
}
