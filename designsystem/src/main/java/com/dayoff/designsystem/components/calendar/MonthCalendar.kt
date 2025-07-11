package com.dayoff.designsystem.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dayoff.designsystem.model.DayCellIndicatorType
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.designsystem.theme.LocalTialShapes
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun MonthCalendar(
    modifier: Modifier = Modifier,
    yearMonth: YearMonth,
    today: LocalDate = LocalDate.now(),
    indicatorResolver: (LocalDate) -> DayCellIndicatorType = { DayCellIndicatorType.NONE },
    onPrevMonth: () -> Unit = {},
    onNextMonth: () -> Unit = {},
    onRefresh: () -> Unit = {},
) {
    val color = LocalTialColors.current
    val shape = LocalTialShapes.current

    val days = makeCalendarDaysForMonth(
        year = yearMonth.year,
        month = yearMonth.monthValue,
        today = today,
        indicatorResolver = indicatorResolver
    )
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
            onRefresh = onRefresh
        )

        Spacer(Modifier.height(16.dp))

        CalendarDayOfWeek()

        Spacer(Modifier.height(16.dp))

        CalendarGrid(days = days, onDayClick = { _, _ -> })

        Spacer(modifier = Modifier.height(16.dp))

        CalendarLegend()
    }
}