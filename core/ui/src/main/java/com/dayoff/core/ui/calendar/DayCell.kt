package com.dayoff.core.ui.calendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dayoff.core.model.calendar.DayCellIndicatorType
import com.dayoff.core.model.calendar.DayCellType
import com.dayoff.core.ui.R
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.designsystem.theme.LocalTialShapes
import com.dayoff.designsystem.theme.LocalTialTypes

@Composable
fun DayCell(
    modifier: Modifier = Modifier,
    day: Int,
    cellType: DayCellType = DayCellType.ENABLED,
    indicators: List<DayCellIndicatorType> = listOf(DayCellIndicatorType.NONE),
    onClick: () -> Unit = {},
) {
    val color = LocalTialColors.current
    val typography = LocalTialTypes.current
    val shape = LocalTialShapes.current

    val hasAnnualLeave = DayCellIndicatorType.ANNUAL_LEAVE in indicators

    val textColor = when {
        hasAnnualLeave -> color.background.base.white
        cellType == DayCellType.DISABLED -> color.text.disabled.primary
        cellType == DayCellType.TODAY -> color.text.brand.primary
        cellType == DayCellType.SELECTED -> color.text.brand.primaryOn
        else -> color.text.surface.primary
    }

    val backgroundColor = when (cellType) {
        DayCellType.SELECTED -> color.background.brand.primary
        DayCellType.TODAY -> color.background.base.white
        else -> Color.Transparent
    }

    val borderModifier = if (cellType == DayCellType.TODAY) {
        Modifier.border(
            width = 1.dp,
            color = color.background.brand.secondary,
            shape = shape.Full
        )
    } else {
        Modifier
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(size = 28.dp)
                .clip(shape = shape.Full)
                .background(color = backgroundColor, shape = shape.Full)
                .then(other = borderModifier)
                .clickable(
                    enabled = cellType != DayCellType.DISABLED,
                    onClick = onClick
                ),
            contentAlignment = Alignment.Center,
        ) {
            if (day != 0) {
                Image(
                    modifier = Modifier
                        .size(size = 32.dp)
                        .alpha(if (hasAnnualLeave) 1f else 0f),
                    painter = painterResource(R.drawable.ic_leave_clover),
                    contentDescription = null,
                )
                Text(
                    text = day.toString(),
                    color = textColor,
                    style = typography.bodySmall,
                )
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            indicators
                .filter { it != DayCellIndicatorType.ANNUAL_LEAVE }
                .forEachIndexed { index, type ->
                    DayCellIndicator(type = type)
                    if (index < indicators.lastIndex - 1) {
                        Spacer(modifier = Modifier.width(2.dp))
                    }
                }
        }
    }
}