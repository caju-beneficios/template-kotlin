package br.com.caju.domain.shared.utils

import java.time.Duration
import java.time.LocalDateTime
import org.assertj.core.api.Assertions
import org.assertj.core.api.RecursiveComparisonAssert

object MapComparator : Comparator<Map<*, *>> {
    override fun compare(o1: Map<*, *>, o2: Map<*, *>): Int {
        return o1.all { (k, v) -> o2[k] == v }.compareTo(true)
    }
}

object LocalDateTimeComparator : Comparator<LocalDateTime> {
    override fun compare(o1: LocalDateTime, o2: LocalDateTime): Int {
        return Duration.between(o1, o2).abs().seconds.let {
            when {
                it <= 5 -> 0
                o1.isBefore(o2) -> -1
                else -> 1
            }
        }
    }
}

fun <T> assertThat(actual: T): RecursiveComparisonAssert<*> =
    Assertions.assertThat(actual)
        .usingRecursiveComparison()
        .withComparatorForType(LocalDateTimeComparator, LocalDateTime::class.java)
        .withComparatorForType(MapComparator, Map::class.java)
        .ignoringCollectionOrder()

infix fun <T> T.shouldBeIgnoringOrder(expected: T): RecursiveComparisonAssert<*> =
    assertThat(this).isEqualTo(expected)

infix fun <T> T.shouldBeIgnoringOrderAndNonDeterministicFields(
    expected: T
): RecursiveComparisonAssert<*> =
    assertThat(this)
        .ignoringFieldsMatchingRegexes(".*createdAt", ".*modifiedAt")
        .isEqualTo(expected)
