package com.dayoff.designsystem.theme.colors

import androidx.compose.ui.graphics.Color

object BackgroundColor {
    val base = Base
    val brand = Brand
    val surface = Surface
    val disabled = Disabled
    val success = Success
    val warning = Warning
    val danger = Danger
    val utilities = Utilities

    object Base {
        val primary: Color get() = BaseWhite
        val white: Color get() = BaseWhite
    }

    object Brand {
        val primary: Color get() = Brand500
        val secondary: Color get() = Brand100
        val tertiary: Color get() = Brand50
        val overlay: Color get() = Color(0xFFCBF0DB).copy(0.4f) // 40%
    }

    object Surface {
        val primary: Color get() = Slate50
        val secondary: Color get() = Slate100
        val tertiary: Color get() = Slate200
    }

    object Disabled {
        val primary: Color get() = Slate300
    }

    object Success {
        val primary: Color get() = Blue50
    }

    object Warning {
        val primary: Color get() = Amber50
        val default: Color get() = Amber500
    }

    object Danger {
        val primary: Color get() = Red50
    }

    object Utilities {
        val dimmed: Color get() = Color(0xFF000000).copy(alpha = 0.4f) // 40%
        val toast: Color get() = Color(0xFF000000).copy(alpha = 0.8f) // 80%
        val loading: Color get() = Color(0xFFFFFFFF).copy(alpha = 0.6f) // 60%
        val tooltip: Color get() = Brand900
    }
}