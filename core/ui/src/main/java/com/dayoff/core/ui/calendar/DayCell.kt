package com.dayoff.core.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dayoff.core.model.calendar.DayCellIndicatorType
import com.dayoff.core.model.calendar.DayCellType
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.designsystem.theme.LocalTialTypes

@Composable
fun DayCell(
    modifier: Modifier = Modifier,
    day: Int,
    cellType: DayCellType = DayCellType.ENABLED,
    indicatorType: DayCellIndicatorType = DayCellIndicatorType.NONE,
    onClick: () -> Unit = {},
) {
    val color = LocalTialColors.current
    val type = LocalTialTypes.current

    val textColor = when (cellType) {
        DayCellType.DISABLED -> color.text.disabled.primary
        DayCellType.TODAY -> color.text.brand.primary
        DayCellType.SELECTED -> color.text.brand.primaryOn
        DayCellType.ENABLED -> color.text.surface.primary
    }
    val bgColor = when (cellType) {
        DayCellType.SELECTED -> color.background.brand.primary
        DayCellType.TODAY -> color.background.base.white
        else -> Color.Transparent
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(size = 28.dp)
                .clip(shape = CircleShape)
                .background(color = bgColor)
                .clickable(enabled = cellType != DayCellType.DISABLED, onClick = onClick),
            contentAlignment = Alignment.Center,
        ) {
            if (day != 0) {
                Text(
                    text = day.toString(), color = textColor, style = type.bodySmall
                )
            }
        }
        DayCellIndicator(indicatorType = indicatorType)
    }
}

@Composable
fun DayCellIndicator(
    modifier: Modifier = Modifier,
    size: Dp = 6.dp,
    indicatorType: DayCellIndicatorType,
    showLabel: Boolean = false,
) {
    val color = LocalTialColors.current
    val type = LocalTialTypes.current

    val indicatorBackground = when (indicatorType) {
        DayCellIndicatorType.NONE -> listOf(Color.Unspecified)
        DayCellIndicatorType.HOLIDAY -> listOf(color.icon.danger.primary)
        DayCellIndicatorType.SUBSTITUTE_HOLIDAY -> listOf(color.background.warning.default)
        DayCellIndicatorType.ANNUAL_LEAVE -> listOf(color.background.brand.primary)
        DayCellIndicatorType.ANNUAL_LEAVE_RECOMMEND -> listOf(
            color.background.brand.overlay, color.background.brand.primary
        )
    }
    val backgroundModifier = when (indicatorBackground.size) {
        0 -> Modifier.background(color = Color.Unspecified)
        1 -> Modifier.background(color = indicatorBackground.first())
        else -> Modifier.background(brush = Brush.linearGradient(colors = indicatorBackground))
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = modifier
                .size(size = size)
                .clip(shape = CircleShape)
                .then(other = backgroundModifier)
        )

        if (showLabel) {

            Spacer(modifier = Modifier.width(width = 6.dp))
            Text(indicatorType.label, color = color.text.surface.tertiary, style = type.bodySmall)
        }
    }
}
