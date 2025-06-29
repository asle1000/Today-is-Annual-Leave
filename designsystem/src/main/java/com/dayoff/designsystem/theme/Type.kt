package com.dayoff.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.dayoff.designsystem.R

val pretendard = FontFamily(
    Font(R.font.pretendard_thin, FontWeight.Thin),
    Font(R.font.pretendard_extra_light, FontWeight.ExtraLight),
    Font(R.font.pretendard_light, FontWeight.Light),
    Font(R.font.pretendard_regular, FontWeight.Normal),
    Font(R.font.pretendard_medium, FontWeight.Medium),
    Font(R.font.pretendard_semi_bold, FontWeight.SemiBold),
    Font(R.font.pretendard_bold, FontWeight.Bold),
    Font(R.font.pretendard_extra_bold, FontWeight.ExtraBold),
    Font(R.font.pretendard_black, FontWeight.Black),
)

val Typography = Typography().copy(
    displayLarge = Typography().displayLarge.copy(fontFamily = pretendard),
    displayMedium = Typography().displayMedium.copy(fontFamily = pretendard),
    displaySmall = Typography().displaySmall.copy(fontFamily = pretendard),
    headlineLarge = Typography().headlineLarge.copy(fontFamily = pretendard),
    headlineMedium = Typography().headlineMedium.copy(fontFamily = pretendard),
    headlineSmall = Typography().headlineSmall.copy(fontFamily = pretendard),
    titleLarge = Typography().titleLarge.copy(fontFamily = pretendard),
    titleMedium = Typography().titleMedium.copy(fontFamily = pretendard),
    titleSmall = Typography().titleSmall.copy(fontFamily = pretendard),
    bodyLarge = Typography().bodyLarge.copy(fontFamily = pretendard),
    bodyMedium = Typography().bodyMedium.copy(fontFamily = pretendard),
    bodySmall = Typography().bodySmall.copy(fontFamily = pretendard),
    labelLarge = Typography().labelLarge.copy(fontFamily = pretendard),
    labelMedium = Typography().labelMedium.copy(fontFamily = pretendard),
    labelSmall = Typography().labelSmall.copy(fontFamily = pretendard),
) 