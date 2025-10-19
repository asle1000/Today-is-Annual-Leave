package com.dayoff.core.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.designsystem.theme.LocalTialTypes

@Composable
fun DayCellIndicator(
    modifier: Modifier = Modifier,
    size: Dp = 6.dp,
    type: DayCellIndicatorType,
    showLabel: Boolean = false,
) {
    val colors = LocalTialColors.current
    val typography = LocalTialTypes.current

    val indicatorColors = when (type) {
        DayCellIndicatorType.NONE -> listOf(Color.Unspecified)
        DayCellIndicatorType.HOLIDAY -> listOf(colors.icon.danger.primary)
        DayCellIndicatorType.SUBSTITUTE_HOLIDAY -> listOf(colors.background.warning.default)
        DayCellIndicatorType.ANNUAL_LEAVE_RECOMMEND -> listOf(colors.leave.half)
        DayCellIndicatorType.ANNUAL_LEAVE -> listOf(colors.leave.annual)
        else -> listOf(Color.Unspecified)
    }

    val backgroundModifier = if (indicatorColors.size == 1) {
        Modifier.background(indicatorColors.first())
    } else {
        Modifier.background(Brush.linearGradient(indicatorColors))
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = modifier
                .size(size)
                .clip(CircleShape)
                .then(backgroundModifier)
        )

        if (showLabel) {
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = type.label,
                color = colors.text.surface.tertiary,
                style = typography.bodySmall
            )
        }
    }
}