package com.dayoff.feature.year_management.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dayoff.feature.year_management.R

@Composable
fun HorizontalPagerIndicator(
    modifier: Modifier = Modifier,
    total: Int = 5,
    current: Int = 0
) {
    Row(
        modifier = modifier.wrapContentWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(total) { index ->
            AnimatedContent(
                targetState = index <= current,
                transitionSpec = {
                    fadeIn(tween(300)) togetherWith fadeOut(tween(300))
                },
                label = stringResource(R.string.DESCRIPTION_PAGER_ANIMATION_LABEL)
            ) { isActive ->
                val (icon, description) = if (isActive) {
                    R.drawable.ic_step_activate to stringResource(R.string.DESCRIPTION_ICON_STEP_ACTIVATE)
                } else {
                    R.drawable.ic_steop_inactivate to stringResource(R.string.DESCRIPTION_ICON_STEP_INACTIVATE)
                }

                Image(
                    painter = painterResource(id = icon),
                    contentDescription = description
                )
            }
        }
    }
}
