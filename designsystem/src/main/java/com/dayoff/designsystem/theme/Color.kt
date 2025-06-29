package com.dayoff.designsystem.theme

import androidx.compose.runtime.staticCompositionLocalOf
import com.dayoff.designsystem.theme.colors.BackgroundColor
import com.dayoff.designsystem.theme.colors.BorderColor
import com.dayoff.designsystem.theme.colors.ButtonColor
import com.dayoff.designsystem.theme.colors.IconColor
import com.dayoff.designsystem.theme.colors.TextColor
import com.dayoff.designsystem.theme.colors.TialBackgroundColors
import com.dayoff.designsystem.theme.colors.TialBaseBackgroundColors
import com.dayoff.designsystem.theme.colors.TialBaseBorderColors
import com.dayoff.designsystem.theme.colors.TialBorderColors
import com.dayoff.designsystem.theme.colors.TialBrandBackgroundColors
import com.dayoff.designsystem.theme.colors.TialBrandBorderColors
import com.dayoff.designsystem.theme.colors.TialBrandButtonBackgroundColors
import com.dayoff.designsystem.theme.colors.TialBrandButtonColors
import com.dayoff.designsystem.theme.colors.TialBrandButtonIconColors
import com.dayoff.designsystem.theme.colors.TialBrandButtonTextColors
import com.dayoff.designsystem.theme.colors.TialBrandTextColors
import com.dayoff.designsystem.theme.colors.TialButtonColors
import com.dayoff.designsystem.theme.colors.TialColors
import com.dayoff.designsystem.theme.colors.TialDangerBackgroundColors
import com.dayoff.designsystem.theme.colors.TialDangerBorderColors
import com.dayoff.designsystem.theme.colors.TialDangerTextColors
import com.dayoff.designsystem.theme.colors.TialDisabledBackgroundColors
import com.dayoff.designsystem.theme.colors.TialDisabledBorderColors
import com.dayoff.designsystem.theme.colors.TialDisabledTextColors
import com.dayoff.designsystem.theme.colors.TialIconColors
import com.dayoff.designsystem.theme.colors.TialSuccessBackgroundColors
import com.dayoff.designsystem.theme.colors.TialSuccessBorderColors
import com.dayoff.designsystem.theme.colors.TialSuccessTextColors
import com.dayoff.designsystem.theme.colors.TialSurfaceBackgroundColors
import com.dayoff.designsystem.theme.colors.TialSurfaceBorderColors
import com.dayoff.designsystem.theme.colors.TialSurfaceTextColors
import com.dayoff.designsystem.theme.colors.TialTextColors
import com.dayoff.designsystem.theme.colors.TialUtilitiesBackgroundColors
import com.dayoff.designsystem.theme.colors.TialWarningBackgroundColors
import com.dayoff.designsystem.theme.colors.TialWarningBorderColors
import com.dayoff.designsystem.theme.colors.TialWarningTextColors

val tialLightColors = TialColors(
    background = TialBackgroundColors(
        base = TialBaseBackgroundColors(
            primary = BackgroundColor.base.primary
        ),
        brand = TialBrandBackgroundColors(
            primary = BackgroundColor.brand.primary,
            secondary = BackgroundColor.brand.secondary,
            tertiary = BackgroundColor.brand.tertiary,
            overlay = BackgroundColor.brand.overlay
        ),
        surface = TialSurfaceBackgroundColors(
            primary = BackgroundColor.surface.primary,
            secondary = BackgroundColor.surface.secondary,
            tertiary = BackgroundColor.surface.tertiary
        ),
        disabled = TialDisabledBackgroundColors(
            primary = BackgroundColor.disabled.primary
        ),
        success = TialSuccessBackgroundColors(
            primary = BackgroundColor.success.primary
        ),
        warning = TialWarningBackgroundColors(
            primary = BackgroundColor.warning.primary
        ),
        danger = TialDangerBackgroundColors(
            primary = BackgroundColor.danger.primary
        ),
        utilities = TialUtilitiesBackgroundColors(
            dimmed = BackgroundColor.utilities.dimmed,
            toast = BackgroundColor.utilities.toast,
            loading = BackgroundColor.utilities.loading,
            tooltip = BackgroundColor.utilities.tooltip
        )
    ),
    text = TialTextColors(
        brand = TialBrandTextColors(
            primary = TextColor.brand.primary,
            primaryOn = TextColor.brand.primaryOn,
            secondary = TextColor.brand.secondary,
            secondaryOn = TextColor.brand.secondaryOn
        ),
        danger = TialDangerTextColors(
            primary = TextColor.danger.primary,
            primaryOn = TextColor.danger.primaryOn
        ),
        disabled = TialDisabledTextColors(
            primary = TextColor.disabled.primary,
            primaryOn = TextColor.disabled.primaryOn
        ),
        success = TialSuccessTextColors(
            primary = TextColor.success.primary,
            primaryOn = TextColor.success.primaryOn
        ),
        warning = TialWarningTextColors(
            primary = TextColor.warning.primary,
            primaryOn = TextColor.warning.primaryOn
        ),
        surface = TialSurfaceTextColors(
            primary = TextColor.surface.primary,
            primaryOn = TextColor.surface.primaryOn,
            secondary = TextColor.surface.secondary,
            tertiary = TextColor.surface.tertiary,
            placeholder = TextColor.surface.placeholder
        )
    ),
    icon = TialIconColors(
        brand = TialBrandTextColors(
            primary = IconColor.brand.primary,
            primaryOn = IconColor.brand.primaryOn,
            secondary = IconColor.brand.secondary,
            secondaryOn = IconColor.brand.secondaryOn
        ),
        danger = TialDangerTextColors(
            primary = IconColor.danger.primary,
            primaryOn = IconColor.danger.primaryOn
        ),
        disabled = TialDisabledTextColors(
            primary = IconColor.disabled.primary,
            primaryOn = IconColor.disabled.primaryOn
        ),
        success = TialSuccessTextColors(
            primary = IconColor.success.primary,
            primaryOn = IconColor.success.primaryOn
        ),
        warning = TialWarningTextColors(
            primary = IconColor.warning.primary,
            primaryOn = IconColor.warning.primaryOn
        ),
        surface = TialSurfaceTextColors(
            primary = IconColor.surface.primary,
            primaryOn = IconColor.surface.primaryOn,
            secondary = IconColor.surface.secondary,
            tertiary = IconColor.surface.tertiary,
            placeholder = IconColor.surface.placeholder
        )
    ),
    border = TialBorderColors(
        base = TialBaseBorderColors(
            input = BorderColor.base.input
        ),
        surface = TialSurfaceBorderColors(
            primary = BorderColor.surface.primary,
            secondary = BorderColor.surface.secondary,
            tertiary = BorderColor.surface.tertiary
        ),
        brand = TialBrandBorderColors(
            primary = BorderColor.brand.primary,
            secondary = BorderColor.brand.secondary,
            tertiary = BorderColor.brand.tertiary
        ),
        disabled = TialDisabledBorderColors(
            primary = BorderColor.disabled.primary
        ),
        success = TialSuccessBorderColors(
            primary = BorderColor.success.primary
        ),
        warning = TialWarningBorderColors(
            primary = BorderColor.warning.primary
        ),
        danger = TialDangerBorderColors(
            primary = BorderColor.danger.primary
        )
    ),
    button = TialButtonColors(
        brand = TialBrandButtonColors(
            background = TialBrandButtonBackgroundColors(
                primary = ButtonColor.brand.background.primary,
                secondary = ButtonColor.brand.background.secondary,
                disabled = ButtonColor.brand.background.disabled
            ),
            text = TialBrandButtonTextColors(
                primaryOn = ButtonColor.brand.text.primaryOn,
                secondaryOn = ButtonColor.brand.text.secondaryOn,
                disabledOn = ButtonColor.brand.text.disabledOn
            ),
            icon = TialBrandButtonIconColors(
                primaryOn = ButtonColor.brand.icon.primaryOn,
                secondaryOn = ButtonColor.brand.icon.secondaryOn,
                disabledOn = ButtonColor.brand.icon.disabledOn
            )
        )
    )
)


val LocalTialColors = staticCompositionLocalOf {
    tialLightColors
}