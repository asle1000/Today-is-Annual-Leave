package com.dayoff.designsystem.components.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dayoff.designsystem.model.DayCellIndicatorType
import com.dayoff.designsystem.model.DayCellType
import java.time.LocalDate
import java.time.YearMonth

enum class MonthType { PREV, CURRENT, NEXT }

data class CalendarDay(
    val day: Int,
    val monthType: MonthType,
    val cellType: DayCellType,
    val indicatorType: DayCellIndicatorType
)

@Composable
fun CalendarGrid(
    days: List<CalendarDay>,
    onDayClick: (Int, MonthType) -> Unit,
    modifier: Modifier = Modifier
) {
    val weeks = days.chunked(7)
    Column(modifier = modifier) {
        weeks.forEach { week ->
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                week.forEach { day ->
                    DayCell(
                        day = day.day,
                        cellType = day.cellType,
                        indicatorType = day.indicatorType,
                        onClick = { onDayClick(day.day, day.monthType) }
                    )
                }
            }

            if(weeks.last() != week) Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

fun makeCalendarDaysForMonth(
    year: Int,
    month: Int,
    today: LocalDate = LocalDate.now(),
    indicatorResolver: (LocalDate) -> DayCellIndicatorType = { DayCellIndicatorType.NONE }
): List<CalendarDay> {
    val yearMonth = YearMonth.of(year, month)
    return generateCalendarDays(yearMonth, today, indicatorResolver)
}

private fun generateCalendarDays(
    yearMonth: YearMonth,
    today: LocalDate,
    indicatorResolver: (LocalDate) -> DayCellIndicatorType
): List<CalendarDay> {
    val firstDayOfMonth = yearMonth.atDay(1)
    val lastDayOfMonth = yearMonth.atEndOfMonth()
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7 // 일요일=0, 월요일=1 ...

    val days = mutableListOf<CalendarDay>()

    val prevMonth = yearMonth.minusMonths(1)
    val prevMonthLastDay = prevMonth.atEndOfMonth().dayOfMonth
    repeat(firstDayOfWeek) { i ->
        days.add(
            CalendarDay(
                day = prevMonthLastDay - firstDayOfWeek + 1 + i,
                monthType = MonthType.PREV,
                cellType = DayCellType.DISABLED,
                indicatorType = DayCellIndicatorType.NONE
            )
        )
    }

    for (day in 1..lastDayOfMonth.dayOfMonth) {
        val date = yearMonth.atDay(day)
        days.add(
            CalendarDay(
                day = day,
                monthType = MonthType.CURRENT,
                cellType = if (date == today) DayCellType.TODAY else DayCellType.ENABLED,
                indicatorType = indicatorResolver(date)
            )
        )
    }

    val remaining = (7 - days.size % 7).let { if (it == 7) 0 else it }
    for (day in 1..remaining) {
        days.add(
            CalendarDay(
                day = day,
                monthType = MonthType.NEXT,
                cellType = DayCellType.DISABLED,
                indicatorType = DayCellIndicatorType.NONE
            )
        )
    }

    return days
}