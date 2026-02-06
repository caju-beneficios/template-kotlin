package br.com.caju.domain.shared.utils

import io.kotest.property.Arb
import io.kotest.property.arbitrary.bind
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.single
import io.kotest.property.arbitrary.uuid
import io.kotest.property.resolution.GlobalArbResolver
import java.util.UUID

inline fun <reified T : Any> random(): T = Arb.registryResolver().bind<T>().single()

inline fun <reified T : Any> randomList(size: Int = 1): List<T> =
    Arb.registryResolver().bind<T>().let { gen -> List(size) { gen.next() } }

fun Arb.Companion.registryResolver() = apply { GlobalArbResolver.register<UUID>(Arb.uuid()) }
