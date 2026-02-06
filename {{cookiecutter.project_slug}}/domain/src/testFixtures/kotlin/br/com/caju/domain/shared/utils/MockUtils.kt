package br.com.caju.domain.shared.utils

import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import java.time.LocalDateTime
import java.util.UUID

fun mockUUID(uuid: UUID = UUID.randomUUID()): UUID {
    mockkStatic(UUID::class)
    every { UUID.randomUUID() } returns uuid
    return uuid
}

fun unmockUUID() {
    runCatching { unmockkStatic(UUID::class) }.getOrNull()
}

suspend fun <T> withUUID(uuid: UUID = UUID.randomUUID(), block: suspend (UUID) -> T): T? {
    mockUUID(uuid)
    return block.invoke(uuid).also { unmockUUID() }
}

fun mockLocalDateTime(localDateTime: LocalDateTime = LocalDateTime.now()): LocalDateTime {
    mockkStatic(LocalDateTime::class)
    every { LocalDateTime.now() } returns localDateTime
    return localDateTime
}

fun unmockLocalDateTime() {
    runCatching { unmockkStatic(LocalDateTime::class) }.getOrNull()
}

suspend fun <T> withLocalDateTime(
    localDateTime: LocalDateTime = LocalDateTime.now(),
    block: suspend (LocalDateTime) -> T,
): T? {
    mockLocalDateTime(localDateTime)
    return block.invoke(localDateTime).also { unmockLocalDateTime() }
}
