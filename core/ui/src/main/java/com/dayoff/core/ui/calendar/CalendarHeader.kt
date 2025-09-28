package com.dayoff.core.ui.calendar

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.dayoff.core.ui.R
import com.dayoff.core.ui.model.OverviewType
import com.dayoff.core.ui.utils.CircleRippleButton
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.designsystem.theme.LocalTialShapes
import com.dayoff.designsystem.theme.LocalTialTypes

/**
 * Calendar header
 *
 * @param modifier
 * @param year
 * @param month
 * @param overviewType
 * @param onSelectedChange
 * @param onPrevMonth
 * @param onNextMonth
 * @receiver
 * @receiver
 * @receiver
 */
@Composable
fun CalendarHeader(
    modifier: Modifier = Modifier,
    year: Int,
    month: Int,
    overviewType: OverviewType,
    onSelectedChange: (OverviewType) -> Unit = {},
    onPrevMonth: () -> Unit,
    onNextMonth: () -> Unit,
) {
    val type = LocalTialTypes.current
    val color = LocalTialColors.current

    val btnColor = when (overviewType) {
        OverviewType.CALENDAR -> color.icon.surface.secondary
        OverviewType.STAMP -> color.icon.disabled.primary
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CircleRippleButton(
                contentPadding = 4.dp,
                enabled = (overviewType == OverviewType.CALENDAR),
                clickable = onPrevMonth,
            ) {
                Icon(
                    modifier = Modifier.size(size = 24.dp),
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    tint = btnColor,
                    contentDescription = stringResource(R.string.DESCRIPTION_ICON_PREVIOUS),
                )
            }

            Spacer(modifier = Modifier.size(size = 6.dp))

            Text(
                text = "$year. ${month.toString().padStart(2, '0')}",
                color = color.text.surface.primary,
                style = type.labelLarge,
            )

            Spacer(modifier = Modifier.size(size = 6.dp))

            CircleRippleButton(
                contentPadding = 4.dp,
                enabled = (overviewType == OverviewType.CALENDAR),
                clickable = onNextMonth,
            ) {
                Icon(
                    modifier = Modifier.size(size = 24.dp),
                    painter = painterResource(id = R.drawable.ic_arrow_forward),
                    tint = btnColor,
                    contentDescription = stringResource(R.string.DESCRIPTION_ICON_FORWARD),
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        CircleRippleButton(
            modifier = Modifier.padding(horizontal = 8.dp),
            contentPadding = 8.dp,
            clickable = {

            },
        ) {
            Icon(
                modifier = Modifier.size(size = 16.dp),
                painter = painterResource(id = R.drawable.ic_edit_square),
                contentDescription = stringResource(R.string.DESCRIPTION_ICON_EDIT_SQUARE),
            )
        }

        OverviewSwitch(
            selectedType = overviewType,
            onSelectedChange = { onSelectedChange(it) },
        )
    }
}

/**
 * Overview switch
 *
 * @param modifier
 * @param selectedType
 * @param onSelectedChange
 * @param enabled
 * @receiver
 */
@Composable
fun OverviewSwitch(
    modifier: Modifier = Modifier,
    selectedType: OverviewType,
    onSelectedChange: (OverviewType) -> Unit,
    enabled: Boolean = true,
) {
    val colors = LocalTialColors.current
    val shape = LocalTialShapes.current
    val density = LocalDensity.current

    var contentSizePx by remember { mutableStateOf(IntSize.Zero) }

    val slotWidthPx = contentSizePx.width / OverviewType.entries.size
    val slotHeightPx = contentSizePx.height

    val targetPositionX = if (selectedType.ordinal == 0) {
        2
    } else {
        selectedType.ordinal * slotWidthPx
    }

    val thumbPositionX by animateIntAsState(
        targetValue = targetPositionX,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow,
        ),
        label = stringResource(R.string.DESCRIPTION_LABEL_SWITCH_THUMB),
    )

    val thumbWidthDp = with(density) { slotWidthPx.coerceAtLeast(1).toDp() }
    val thumbHeightDp = with(density) { slotHeightPx.coerceAtLeast(1).toDp() }

    Box(
        modifier = modifier
            .clip(shape = shape.Full)
            .background(color = colors.background.brand.tertiary)
            .semantics {
                role = Role.Switch
                stateDescription = selectedType.name
                disabled().takeIf { !enabled }
            }
            .padding(2.dp),
    ) {
        if (slotWidthPx > 0 && slotHeightPx > 0) {
            Box(
                modifier = Modifier
                    .align(alignment = Alignment.CenterStart)
                    .offset { IntOffset(x = thumbPositionX, y = 0) }
                    .size(
                        width = thumbWidthDp.minus(other = 2.dp),
                        height = thumbHeightDp.minus(other = 2.dp),
                    )
                    .clip(shape = shape.Full)
                    .background(color = colors.background.brand.primary),
            )
        }

        Row(
            modifier = Modifier
                .onGloballyPositioned { coords -> contentSizePx = coords.size }
                .wrapContentSize()
                .align(alignment = Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OverviewType.entries.map { type ->
                SwitchButton(
                    enabled = enabled,
                    overviewType = type,
                    selected = selectedType == type,
                    onSelectedChange = onSelectedChange,
                )
            }
        }
    }
}

/**
 * Overview Switch button
 *
 * @param enabled
 * @param overviewType
 * @param selected
 * @param onSelectedChange
 * @receiver
 */
@Composable
private fun SwitchButton(
    enabled: Boolean,
    overviewType: OverviewType,
    selected: Boolean,
    onSelectedChange: (OverviewType) -> Unit
) {
    val color = LocalTialColors.current

    Box(
        modifier = Modifier
            .defaultMinSize(minWidth = 40.dp, minHeight = 32.dp)
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable(
                enabled = enabled,
                role = Role.Button,
                indication = null,
                interactionSource = null,
                onClick = {
                    onSelectedChange(overviewType)
                },
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier.size(size = 16.dp),
            painter = painterResource(id = overviewType.iconResource),
            tint = if (selected) color.icon.surface.primaryOn else color.icon.surface.secondary,
            contentDescription = stringResource(id = overviewType.descriptionResource),
        )
    }
}