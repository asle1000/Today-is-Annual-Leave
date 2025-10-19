package com.dayoff.core.ui.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dayoff.core.model.calendar.CalendarDay
import com.dayoff.core.model.calendar.MonthType

@Composable
fun CalendarGrid(
    days: List<CalendarDay>,
    onDayClick: (Int, MonthType) -> Unit,
    modifier: Modifier = Modifier
) {
    val weeks = days.chunked(7)
    Column(modifier = modifier) {
        weeks.forEach { week ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                week.forEach { day ->
                    DayCell(
                        day = day.day,
                        cellType = day.cellType,
                        indicators = day.indicatorType,
                        onClick = { onDayClick(day.day, day.monthType) }
                    )
                }
            }

            if (weeks.last() != week) Spacer(modifier = Modifier.height(8.dp))
        }
    }
}