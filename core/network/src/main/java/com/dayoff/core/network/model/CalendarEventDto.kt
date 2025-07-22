package com.dayoff.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class CalendarEventDto(
    val month: String,
    @kotlinx.serialization.SerialName("days")
    val specialDays: List<SpecialDay>
)
