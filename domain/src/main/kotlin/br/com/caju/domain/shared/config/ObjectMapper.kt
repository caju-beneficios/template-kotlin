package br.com.caju.domain.shared.config

import com.fasterxml.jackson.annotation.JsonInclude.Include.ALWAYS
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY
import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
import com.fasterxml.jackson.databind.introspect.Annotated
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object ObjectMapperProvider {
    val mapper: ObjectMapper = jacksonObjectMapper().config()

    fun ObjectMapper.config() = apply {
        registerModule(JavaTimeModule())
        setDefaultPropertyInclusion(ALWAYS)
        disable(WRITE_DATES_AS_TIMESTAMPS)
        disable(FAIL_ON_UNKNOWN_PROPERTIES)
        disable(FAIL_ON_READING_DUP_TREE_KEY)
        registerModule(EnumCaseInsensitiveModule())
    }

    @Suppress("UNCHECKED_CAST")
    class EnumCaseInsensitiveModule : SimpleModule() {
        override fun setupModule(context: SetupContext?) {
            super.setupModule(context)

            context?.let {
                val introspector =
                    object : JacksonAnnotationIntrospector() {
                        override fun findDeserializer(a: Annotated?): Any? {
                            val original = super.findDeserializer(a)

                            if (original == null && a?.rawType?.isEnum == true) {
                                return EnumCaseInsensitiveDeserializer(
                                    a.rawType as Class<out Enum<*>>
                                )
                            }

                            return original
                        }
                    }

                it.insertAnnotationIntrospector(introspector)
            }
        }

        internal class EnumCaseInsensitiveDeserializer<T : Enum<T>>(
            private val enumClass: Class<T>
        ) : JsonDeserializer<T>() {
            override fun deserialize(p0: JsonParser?, p1: DeserializationContext?): T =
                p0?.text?.let { value ->
                    val enumConstants = enumClass.enumConstants
                    val values = mutableMapOf<String, T>()

                    enumConstants.forEach { constant ->
                        val jsonValueMethod =
                            enumClass.methods.find { it.isAnnotationPresent(JsonValue::class.java) }
                        val enumValue =
                            jsonValueMethod?.invoke(constant) as? String ?: constant.name
                        values[enumValue.lowercase()] = constant
                    }
                    values.getOrDefault(value.lowercase(), null)
                } ?: throw IllegalArgumentException("Invalid enum value")
        }
    }
}
