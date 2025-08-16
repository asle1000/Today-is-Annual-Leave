package com.dayoff.core.ui.model

import java.time.LocalDate

data class Selection(
    val rangeStart: LocalDate? = null,
    val selectedDates: Set<LocalDate> = emptySet()
)