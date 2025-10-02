package com.dayoff.core.ui.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dayoff.core.model.calendar.CalendarDay
import com.dayoff.core.model.calendar.DayOfWeek
import com.dayoff.core.model.calendar.MonthType

@Composable
fun CalendarView(
    modifier: Modifier = Modifier,
    days: List<CalendarDay>,
    startDayOfWeek: DayOfWeek = DayOfWeek.MONDAY,
    onDayClick: (Int, MonthType) -> Unit = { _, _ -> },
) {
    Column(modifier = modifier) {
        CalendarDayOfWeek(modifier = Modifier.padding(vertical = 16.dp),startDayOfWeek = startDayOfWeek)

        CalendarGrid(days = days, onDayClick = onDayClick)

        Spacer(modifier = Modifier.height(16.dp))

        CalendarLegend()
    }
}