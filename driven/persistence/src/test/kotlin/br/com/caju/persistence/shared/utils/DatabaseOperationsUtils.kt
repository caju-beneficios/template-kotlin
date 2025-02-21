package br.com.caju.persistence.shared.utils

import org.springframework.jdbc.core.JdbcTemplate

fun JdbcTemplate.read(query: String): List<String> = query(query) { rs, _ -> rs.getString(1) }

fun JdbcTemplate.write(queries: List<String>) = queries.forEach { query -> execute(query) }
