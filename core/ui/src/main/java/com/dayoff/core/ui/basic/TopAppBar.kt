package com.dayoff.core.ui.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dayoff.core.ui.R
import com.dayoff.core.ui.utils.CircleRippleButton
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.designsystem.theme.LocalTialTypes

/**
 *  Created by KyunghyunPark at 2025. 7. 24.

 */
@Composable
fun TopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    prefixContent: @Composable (() -> Unit)? = null,
    suffixContent: @Composable (() -> Unit)? = null
) {
    val color = LocalTialColors.current
    val type = LocalTialTypes.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = color.background.base.white),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier
                    .size(size = 48.dp)
                    .alpha(alpha = if (prefixContent == null) 0f else 1f),
                contentAlignment = Alignment.Center,
            ) {
                prefixContent?.invoke()
            }



            Text(
                text = title,
                style = type.titleMedium,
                color = color.text.surface.secondary,
            )

            Box(
                modifier = Modifier
                    .size(size = 48.dp)
                    .alpha(alpha = if (suffixContent == null) 0f else 1f),
                contentAlignment = Alignment.Center,
            ) {
                suffixContent?.invoke()
            }
        }
    }
}

@Composable
fun BackIconButton(size: Int = 24, onClick: () -> Unit) {

    CircleRippleButton(
        contentPadding = 0.dp,
        clickable = onClick,
    ) {
        Icon(
            modifier = Modifier.size(size = size.dp),
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = null,
        )
    }
}