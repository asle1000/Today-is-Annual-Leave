package com.dayoff.designsystem.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF60D194),
    onPrimary = Color(0xFFE8F8F0),
    secondary = Color(0xFF6BD0BB),
    onSecondary = Color(0xFF4BB27D),
    tertiary = Color(0xFFE8F8F0),
    onTertiary = Color(0xFFE8F8F0),
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF020617),
    surface = Color(0xFFF8F8FC),
    onSurface = Color(0xFF020617),
    error = Color(0xFFFFE1E2),
    onError = Color(0xFFDC2626),
    surfaceVariant = Color(0xFFFFF1F9),
    onSurfaceVariant = Color(0xFF334155),
    outline = Color(0xFFFFF1F9),
    outlineVariant = Color(0xFFE2E8F0)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF60D194),
    onPrimary = Color(0xFFE8F8F0),
    secondary = Color(0xFF6BD0BB),
    onSecondary = Color(0xFF4BB27D),
    tertiary = Color(0xFFE8F8F0),
    onTertiary = Color(0xFFE8F8F0),
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF020617),
    surface = Color(0xFFF8F8FC),
    onSurface = Color(0xFF020617),
    error = Color(0xFFFFE1E2),
    onError = Color(0xFFDC2626),
    surfaceVariant = Color(0xFFFFF1F9),
    onSurfaceVariant = Color(0xFF334155),
    outline = Color(0xFFFFF1F9),
    outlineVariant = Color(0xFFE2E8F0)
)

@Composable
fun TodayIsAnnualLeaveTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    } else {
        if (darkTheme) DarkColorScheme else LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
} 