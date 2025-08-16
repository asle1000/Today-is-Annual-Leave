package com.dayoff.core.ui.model

import java.time.LocalDate

sealed interface CalendarAction {
    data class SelectionChanged(
        val next: Selection,
        val added: Set<LocalDate>,
        val removed: Set<LocalDate>
    ) : CalendarAction
    data class ShowInspect(val date: LocalDate) : CalendarAction
    data object None : CalendarAction
}