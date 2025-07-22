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
        val input: Color get() = Slate200
    }
    
    object Surface {
        val primary: Color get() = Slate100
        val secondary: Color get() = Slate200
        val tertiary: Color get() = Slate300
    }
    
    object Brand {
        val primary: Color get() = Brand500
        val secondary: Color get() = Brand300
        val tertiary: Color get() = Brand50
    }
    
    object Disabled {
        val primary: Color get() = Slate200
    }
    
    object Success {
        val primary: Color get() = Blue500
    }
    
    object Warning {
        val primary: Color get() = Amber500
    }
    
    object Danger {
        val primary: Color get() = Red500
    }
}