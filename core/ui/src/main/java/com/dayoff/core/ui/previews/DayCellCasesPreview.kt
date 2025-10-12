package com.dayoff.core.ui.previews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dayoff.core.model.calendar.DayCellIndicatorType
import com.dayoff.core.model.calendar.DayCellType
import com.dayoff.core.ui.calendar.DayCell
import com.dayoff.core.ui.calendar.DayCellIndicator

@Preview(showBackground = true)
@Composable
fun PreviewAllDayCellCases() {
    val cellTypes = DayCellType.entries.toTypedArray()
    val indicatorTypes = DayCellIndicatorType.entries.toTypedArray()
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.background(Color.LightGray)
    ) {
        Row {
            indicatorTypes.forEach { indicatorType ->
                DayCellIndicator(type = indicatorType, size = 8.dp, showLabel = true)
                Spacer(Modifier.width(16.dp))
            }
        }
        cellTypes.forEach { cellType ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DayCell(
                    day = 10,
                    cellType = cellType,
                    indicators = indicatorTypes.toList(),
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}
