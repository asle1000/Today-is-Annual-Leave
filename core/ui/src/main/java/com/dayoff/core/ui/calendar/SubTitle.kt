package com.dayoff.core.ui.calendar

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.designsystem.theme.LocalTialTypes

@Composable
fun SubTitle(text: String) {
    val style = LocalTialTypes.current
    val color = LocalTialColors.current

    Text(
        text = text, style = style.titleLarge.copy(
            color = color.text.surface.primary,
            fontWeight = FontWeight.SemiBold,
        )
    )
}