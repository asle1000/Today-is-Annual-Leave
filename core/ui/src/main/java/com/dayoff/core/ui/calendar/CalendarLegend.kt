package com.dayoff.core.ui.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dayoff.core.model.calendar.DayCellIndicatorType

@Composable
fun CalendarLegend(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DayCellIndicatorType.entries.filter {
            it != DayCellIndicatorType.NONE
        }.map { type ->
            DayCellIndicator(indicatorType = type, showLabel = true)
        }
    }
}
