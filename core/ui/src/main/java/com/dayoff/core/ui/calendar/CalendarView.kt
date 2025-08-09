package com.dayoff.core.ui.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.dayoff.core.model.calendar.CalendarDay
import java.time.YearMonth

@Composable
fun CalendarView(
    yearMonth: YearMonth,
    list: List<CalendarDay>,
    onChanged: (Int, Int) -> Unit,
) {
    Column {
        MonthCalendar(days = list, yearMonth = yearMonth, onPrevMonth = {
            if (yearMonth.monthValue > 1) {
                val changedYearMonth = yearMonth.minusMonths(1)
                onChanged(changedYearMonth.year, changedYearMonth.monthValue)
            }
        }, onNextMonth = {
            if (yearMonth.monthValue < 12) {
                val nextYearMonth = yearMonth.plusMonths(1)
                onChanged(nextYearMonth.year, nextYearMonth.monthValue)
            }
        }, onRefresh = {

        })
    }
}