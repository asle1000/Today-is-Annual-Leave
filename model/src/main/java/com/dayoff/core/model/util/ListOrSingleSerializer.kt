package com.dayoff.core.model.util

import kotlinx.serialization.Serializable
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.*

/**
 * JSON에서 단일 객체 또는 배열로 올 수 있는 item을 List<T>로 파싱
 */
class ListOrSingleSerializer<T>(
    private val dataSerializer: KSerializer<T>
) : KSerializer<List<T>> {

    override val descriptor: SerialDescriptor =
        ListSerializer(dataSerializer).descriptor

    override fun deserialize(decoder: Decoder): List<T> {
        val input = decoder as? JsonDecoder
            ?: error("ListOrSingleSerializer only supports JSON")
        return when (val element = input.decodeJsonElement()) {
            is JsonArray -> element.map { input.json.decodeFromJsonElement(dataSerializer, it) }
            is JsonObject -> listOf(input.json.decodeFromJsonElement(dataSerializer, element))
            else -> throw SerializationException("Expected JsonArray or JsonObject but got ${element::class.simpleName}")
        }
    }

    override fun serialize(encoder: Encoder, value: List<T>) {
        encoder.encodeSerializableValue(ListSerializer(dataSerializer), value)
    }
}