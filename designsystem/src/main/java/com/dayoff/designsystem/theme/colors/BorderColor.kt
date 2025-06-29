package com.dayoff.designsystem.theme.colors

import androidx.compose.ui.graphics.Color

object BorderColor {
    val base = Base
    val surface = Surface
    val brand = Brand
    val disabled = Disabled
    val success = Success
    val warning = Warning
    val danger = Danger

    object Base {
        val input: Color get() = Color(0xFFE2E8F0)
    }
    
    object Surface {
        val primary: Color get() = Color(0xFFFFF1F9)
        val secondary: Color get() = Color(0xFFE2E8F0)
        val tertiary: Color get() = Color(0xFFCBD5E1)
    }
    
    object Brand {
        val primary: Color get() = Color(0xFF60D194)
        val secondary: Color get() = Color(0xFF83DCB0)
        val tertiary: Color get() = Color(0xFFE8F8F0)
    }
    
    object Disabled {
        val primary: Color get() = Color(0xFFE2E8F0)
    }
    
    object Success {
        val primary: Color get() = Color(0xFF3B8216)
    }
    
    object Warning {
        val primary: Color get() = Color(0xFFF59E0B)
    }
    
    object Danger {
        val primary: Color get() = Color(0xFFEF4444)
    }
}