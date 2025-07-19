package com.dayoff.core.model.calendar

import kotlinx.serialization.Serializable

@Serializable
data class SpecialDay(
    @kotlinx.serialization.SerialName("dateName")
    val name: String,
    @kotlinx.serialization.SerialName("locdate")
    val date: String,
    val type: String,
    val isHoliday: Boolean,
)

@Serializable
data class CalendarEventDto(
    val month: String,
    @kotlinx.serialization.SerialName("days")
    val specialDays: List<SpecialDay>
)
