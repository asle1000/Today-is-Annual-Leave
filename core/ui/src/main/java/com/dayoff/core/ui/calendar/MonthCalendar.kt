package com.dayoff.core.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.designsystem.theme.LocalTialShapes
import java.time.YearMonth

@Composable
fun MonthCalendar(
    modifier: Modifier = Modifier,
    days: List<CalendarDay>,
    yearMonth: YearMonth,
    startDayOfWeek: DayOfWeek = DayOfWeek.MONDAY,
    onDayClick: (Int, MonthType) -> Unit = { _, _ -> },
    onMonthChange: (offset: Int) -> Unit = {},
) {
    val color = LocalTialColors.current
    val shape = LocalTialShapes.current

    Column(
        modifier = modifier
            .background(
                color = color.background.base.white,
                shape = shape.ExtraLarge,
            )
            .border(width = 1.dp, color = color.border.surface.primary, shape = shape.ExtraLarge)
            .padding(all = 20.dp),
    ) {
        CalendarHeader(
            month = yearMonth.monthValue,
            onMonthChange = onMonthChange,
        )

        Spacer(Modifier.height(16.dp))

        CalendarDayOfWeek(startDayOfWeek = startDayOfWeek)

        Spacer(Modifier.height(16.dp))

        CalendarGrid(days = days, onDayClick = onDayClick)

        Spacer(modifier = Modifier.height(16.dp))

        CalendarLegend()
    }
}