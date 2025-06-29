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
        val primary: Color get() = Color(0xFF60D194)
        val primaryOn: Color get() = Color(0xFFE8F8F0)
        val secondary: Color get() = Color(0xFF6BD8A1)
        val secondaryOn: Color get() = Color(0xFF4BB27D)
    }

    object Danger {
        val primary: Color get() = Color(0xFFEF4444)
        val primaryOn: Color get() = Color(0xFFDC2626)
    }

    object Disabled {
        val primary: Color get() = Color(0xFFCBD5E1)
        val primaryOn: Color get() = Color(0xFFFFF1F9)
    }

    object Success {
        val primary: Color get() = Color(0xFF3B8216)
        val primaryOn: Color get() = Color(0xFF2563EB)
    }

    object Warning {
        val primary: Color get() = Color(0xFFF59E0B)
        val primaryOn: Color get() = Color(0xFFD97706)
    }

    object Surface {
        val primary: Color get() = Color(0xFF020617)
        val primaryOn: Color get() = Color(0xFFE8F8F0)
        val secondary: Color get() = Color(0xFF334155)
        val tertiary: Color get() = Color(0xFF64748B)
        val placeholder: Color get() = Color(0xFF94A3B8)
    }
}
