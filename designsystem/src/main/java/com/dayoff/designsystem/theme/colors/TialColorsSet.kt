package com.dayoff.designsystem.theme.colors

import androidx.compose.ui.graphics.Color

data class TialColors(
    val background: TialBackgroundColors,
    val text: TialTextColors,
    val icon: TialIconColors,
    val border: TialBorderColors,
    val button: TialButtonColors
)

data class TialBackgroundColors(
    val base: TialBaseBackgroundColors,
    val brand: TialBrandBackgroundColors,
    val surface: TialSurfaceBackgroundColors,
    val disabled: TialDisabledBackgroundColors,
    val success: TialSuccessBackgroundColors,
    val warning: TialWarningBackgroundColors,
    val danger: TialDangerBackgroundColors,
    val utilities: TialUtilitiesBackgroundColors
)

data class TialBaseBackgroundColors(
    val primary: Color,
    val white: Color
)

data class TialBrandBackgroundColors(
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
    val overlay: Color
)

data class TialSurfaceBackgroundColors(
    val primary: Color,
    val secondary: Color,
    val tertiary: Color
)

data class TialDisabledBackgroundColors(
    val primary: Color
)

data class TialSuccessBackgroundColors(
    val primary: Color
)

data class TialWarningBackgroundColors(
    val primary: Color
)

data class TialDangerBackgroundColors(
    val primary: Color
)

data class TialUtilitiesBackgroundColors(
    val dimmed: Color,
    val toast: Color,
    val loading: Color,
    val tooltip: Color
)

data class TialTextColors(
    val brand: TialBrandTextColors,
    val danger: TialDangerTextColors,
    val disabled: TialDisabledTextColors,
    val success: TialSuccessTextColors,
    val warning: TialWarningTextColors,
    val surface: TialSurfaceTextColors
)

data class TialBrandTextColors(
    val primary: Color,
    val primaryOn: Color,
    val secondary: Color,
    val secondaryOn: Color
)

data class TialDangerTextColors(
    val primary: Color,
    val primaryOn: Color
)

data class TialDisabledTextColors(
    val primary: Color,
    val primaryOn: Color
)

data class TialSuccessTextColors(
    val primary: Color,
    val primaryOn: Color
)

data class TialWarningTextColors(
    val primary: Color,
    val primaryOn: Color
)

data class TialSurfaceTextColors(
    val primary: Color,
    val primaryOn: Color,
    val secondary: Color,
    val tertiary: Color,
    val placeholder: Color
)

data class TialIconColors(
    val brand: TialBrandTextColors,
    val danger: TialDangerTextColors,
    val disabled: TialDisabledTextColors,
    val success: TialSuccessTextColors,
    val warning: TialWarningTextColors,
    val surface: TialSurfaceTextColors
)

data class TialBorderColors(
    val base: TialBaseBorderColors,
    val surface: TialSurfaceBorderColors,
    val brand: TialBrandBorderColors,
    val disabled: TialDisabledBorderColors,
    val success: TialSuccessBorderColors,
    val warning: TialWarningBorderColors,
    val danger: TialDangerBorderColors
)

data class TialBaseBorderColors(
    val input: Color
)

data class TialSurfaceBorderColors(
    val primary: Color,
    val secondary: Color,
    val tertiary: Color
)

data class TialBrandBorderColors(
    val primary: Color,
    val secondary: Color,
    val tertiary: Color
)

data class TialDisabledBorderColors(
    val primary: Color
)

data class TialSuccessBorderColors(
    val primary: Color
)

data class TialWarningBorderColors(
    val primary: Color
)

data class TialDangerBorderColors(
    val primary: Color
)

data class TialButtonColors(
    val brand: TialBrandButtonColors
)

data class TialBrandButtonColors(
    val background: TialBrandButtonBackgroundColors,
    val text: TialBrandButtonTextColors,
    val icon: TialBrandButtonIconColors
)

data class TialBrandButtonBackgroundColors(
    val primary: Color,
    val secondary: Color,
    val disabled: Color
)

data class TialBrandButtonTextColors(
    val primaryOn: Color,
    val secondaryOn: Color,
    val disabledOn: Color
)

data class TialBrandButtonIconColors(
    val primaryOn: Color,
    val secondaryOn: Color,
    val disabledOn: Color
)
