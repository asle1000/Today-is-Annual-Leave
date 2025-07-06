package com.dayoff.designsystem.components.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.dayoff.designsystem.model.DayOfWeek
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.designsystem.theme.LocalTialTypes


@Composable
fun CalendarDayOfWeek(
    modifier: Modifier = Modifier,
    startDayOfWeek: DayOfWeek = DayOfWeek.MONDAY
) {
    val color = LocalTialColors.current
    val type = LocalTialTypes.current
    val days = DayOfWeek.getOrderedDays(start = startDayOfWeek)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        days.forEach { day ->
            Text(
                text = day.label,
                color = color.text.surface.primary,
                style = type.bodySmall,
                modifier = Modifier.weight(weight = 1f, fill = true),
                textAlign = TextAlign.Center
            )
        }
    }
}

