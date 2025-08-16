package com.dayoff.core.model.calendar

data class CalendarDay(
    val day: Int,
    val name: String,
    val monthType: MonthType,
    val cellType: DayCellType,
    val indicatorType: DayCellIndicatorType
)
