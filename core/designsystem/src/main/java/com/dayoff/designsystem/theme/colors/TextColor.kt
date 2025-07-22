package com.dayoff.designsystem.theme.colors

import androidx.compose.ui.graphics.Color

object TextColor {
    val brand = Brand
    val danger = Danger
    val disabled = Disabled
    val success = Success
    val warning = Warning
    val surface = Surface

    object Brand {
        val primary: Color get() = Brand500
        val primaryOn: Color get() = Brand50
        val secondary: Color get() = Brand400
        val secondaryOn: Color get() = Brand600
    }

    object Danger {
        val primary: Color get() = Red500
        val primaryOn: Color get() = Red600
    }

    object Disabled {
        val primary: Color get() = Slate300
        val primaryOn: Color get() = Slate100
    }

    object Success {
        val primary: Color get() = Blue500
        val primaryOn: Color get() = Blue600
    }

    object Warning {
        val primary: Color get() = Amber500
        val primaryOn: Color get() = Amber600
    }

    object Surface {
        val primary: Color get() = Slate900
        val primaryOn: Color get() = Slate100
        val secondary: Color get() = Slate700
        val tertiary: Color get() = Slate500
        val placeholder: Color get() = Slate400
    }
}
