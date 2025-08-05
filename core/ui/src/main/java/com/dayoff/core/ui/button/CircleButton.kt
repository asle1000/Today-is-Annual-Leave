package com.dayoff.core.ui.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 *  Created by KyunghyunPark at 2025. 8. 2.

 */
@Composable
fun CircleButton(
    modifier: Modifier = Modifier,
    borderColor: Color,
    backgroundColor: Color,
    onClick: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .size(64.dp)
            .clip(CircleShape)
            .border(width = 1.dp, color = borderColor, shape = CircleShape)
            .background(backgroundColor)
            .clickable(onClick = onClick),
        content = content
    )
}