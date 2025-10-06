package com.dayoff.core.ui.calendar

/**
 *  Created by KyunghyunPark at 2025. 10. 6.

 */
import androidx.compose.foundation.background
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
    onPrevMonth: () -> Unit = {},
    onNextMonth: () -> Unit = {},
) {
    val color = LocalTialColors.current
    val shape = LocalTialShapes.current

    Column(
        modifier = modifier
            .background(
                color = color.background.surface.primary,
                shape = shape.ExtraLarge,
            )
            .padding(20.dp)
    ) {
        CalendarHeader(
            year = yearMonth.year,
            month = yearMonth.monthValue,
            onPrevMonth = onPrevMonth,
            onNextMonth = onNextMonth,
        )

        Spacer(Modifier.height(16.dp))

        CalendarDayOfWeek(startDayOfWeek = startDayOfWeek)

        Spacer(Modifier.height(16.dp))

        CalendarGrid(days = days, onDayClick = onDayClick)

        Spacer(modifier = Modifier.height(16.dp))

        CalendarLegend()
    }
}