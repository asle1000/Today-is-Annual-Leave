package com.dayoff.core.ui.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dayoff.core.model.calendar.DayOfWeek
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.designsystem.theme.LocalTialTypes


@Composable
fun CalendarDayOfWeek(
    modifier: Modifier = Modifier, startDayOfWeek: DayOfWeek = DayOfWeek.MONDAY
) {
    val color = LocalTialColors.current
    val type = LocalTialTypes.current
    val days = DayOfWeek.getOrderedDays(start = startDayOfWeek)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        days.forEach { day ->
            Text(
                modifier = Modifier.width(28.dp),
                text = day.label,
                color = color.text.surface.primary,
                style = type.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}

