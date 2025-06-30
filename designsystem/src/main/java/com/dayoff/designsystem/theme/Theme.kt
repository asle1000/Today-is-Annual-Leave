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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.dayoff.designsystem.theme.colors.BackgroundColor
import com.dayoff.designsystem.theme.colors.BorderColor
import com.dayoff.designsystem.theme.colors.TextColor

val LightColorScheme = lightColorScheme(
    primary = BackgroundColor.brand.primary, // 앱의 주요 색상 (버튼 등)
    onPrimary = TextColor.brand.primaryOn, // primary 위 텍스트/아이콘
    secondary = BackgroundColor.brand.secondary, // 보조 색상 (FAB, Tab 등)
    onSecondary = TextColor.brand.secondaryOn, // secondary 위 텍스트/아이콘
    tertiary = BackgroundColor.brand.tertiary, // 강조 색상 (알림 등)
    onTertiary = TextColor.surface.primaryOn, // tertiary 위 텍스트
    background = BackgroundColor.base.primary, // 앱 배경 (Scaffold 등)
    onBackground = TextColor.surface.primary, // 배경 위 본문 텍스트
    surface = BackgroundColor.surface.primary, // 카드, 시트, Dialog 등
    onSurface = TextColor.surface.primary, // surface 위 텍스트
    error = BackgroundColor.danger.primary, // 에러 상태
    onError = TextColor.danger.primaryOn, // 에러 위 텍스트
    outline = BorderColor.surface.primary, // 텍스트 필드 등 경계선
    surfaceVariant = BackgroundColor.surface.secondary, // 보조 surface
    onSurfaceVariant = TextColor.surface.secondary, // 보조 surface 텍스트
    inverseSurface = BackgroundColor.surface.tertiary, // 반전 surface (예: dark top bar)
    inverseOnSurface = TextColor.surface.tertiary, // 반전 surface 위 텍스트
    inversePrimary = BackgroundColor.brand.overlay, // 대비 강조용 배경
)

private val DarkColorScheme = darkColorScheme(
    primary = BackgroundColor.brand.primary, // 앱의 주요 색상 (버튼 등)
    onPrimary = TextColor.brand.primaryOn, // primary 위 텍스트/아이콘
    secondary = BackgroundColor.brand.secondary, // 보조 색상 (FAB, Tab 등)
    onSecondary = TextColor.brand.secondaryOn, // secondary 위 텍스트/아이콘
    tertiary = BackgroundColor.brand.tertiary, // 강조 색상 (알림 등)
    onTertiary = TextColor.surface.primaryOn, // tertiary 위 텍스트
    background = BackgroundColor.base.primary, // 앱 배경 (Scaffold 등)
    onBackground = TextColor.surface.primary, // 배경 위 본문 텍스트
    surface = BackgroundColor.surface.primary, // 카드, 시트, Dialog 등
    onSurface = TextColor.surface.primary, // surface 위 텍스트
    error = BackgroundColor.danger.primary, // 에러 상태
    onError = TextColor.danger.primaryOn, // 에러 위 텍스트
    outline = BorderColor.surface.primary, // 텍스트 필드 등 경계선
    surfaceVariant = BackgroundColor.surface.secondary, // 보조 surface
    onSurfaceVariant = TextColor.surface.secondary, // 보조 surface 텍스트
    inverseSurface = BackgroundColor.surface.tertiary, // 반전 surface (예: dark top bar)
    inverseOnSurface = TextColor.surface.tertiary, // 반전 surface 위 텍스트
    inversePrimary = BackgroundColor.brand.overlay, // 대비 강조용 배경
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

    val typographyScheme = LocalTialTypes.current

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
        typography = typographyScheme,
        content = content
    )
} 