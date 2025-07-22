package com.dayoff.core.ui.previews

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dayoff.core.ui.calendar.CalendarDayOfWeek
import com.dayoff.core.model.calendar.DayOfWeek

@Preview(showBackground = true)
@Composable
fun CalendarDayOfWeekPreview() {
    Column {
        CalendarDayOfWeek(startDayOfWeek = DayOfWeek.MONDAY)
        Spacer(Modifier.height(8.dp))
        CalendarDayOfWeek(startDayOfWeek = DayOfWeek.SUNDAY)
    }
}