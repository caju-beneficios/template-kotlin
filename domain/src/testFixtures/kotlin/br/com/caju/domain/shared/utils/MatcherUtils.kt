package br.com.caju.domain.shared.utils

import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.time.temporal.ChronoUnit.MINUTES
import kotlin.reflect.full.memberProperties

inline fun <reified T : Any, reified U : T> T.shouldBeTruncating(
    expected: U?,
    unit: ChronoUnit = MINUTES,
): T {
    return normalized(unit) shouldBe expected.normalized(unit)
}

inline fun <reified T, C : Collection<T>> C?.shouldContainExactlyInAnyOrderTruncating(
    expected: Collection<T>?,
    unit: ChronoUnit = MINUTES,
): List<T>? {
    return this?.map { it.normalized(unit) } shouldContainExactlyInAnyOrder
        expected?.map { it.normalized(unit) }
}

inline fun <reified T> T.normalized(unit: ChronoUnit = MINUTES): T {
    this!!::class.memberProperties.forEach { property ->
        if (property.returnType.classifier == LocalDateTime::class) {
            val field = T::class.java.getDeclaredField(property.name)
            field.isAccessible = true
            field.set(this, (field.get(this) as LocalDateTime).truncatedTo(unit))
        }
    }
    return this
}
