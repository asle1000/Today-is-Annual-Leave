package com.dayoff.core.network.model

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

