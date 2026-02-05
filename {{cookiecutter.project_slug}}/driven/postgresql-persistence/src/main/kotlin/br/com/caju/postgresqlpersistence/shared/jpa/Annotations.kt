package br.com.caju.postgresqlpersistence.shared.jpa

import java.sql.Types.VARCHAR
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FIELD
import org.hibernate.annotations.JdbcTypeCode
import org.springframework.core.annotation.AliasFor

@JdbcTypeCode(VARCHAR)
@Target(FIELD)
@Retention(RUNTIME)
annotation class UUIDColumn(
    @get:AliasFor(annotation = JdbcTypeCode::class, value = "value") val sqlType: Int = VARCHAR
)
