package com.dayoff.designsystem.theme.colors

import androidx.compose.ui.graphics.Color

object ButtonColor {
    val brand = Brand

    object Brand {
        val background = Background
        val text = Text
        val icon = Icon

        object  Background {
            val primary: Color get() = Brand900
            val secondary: Color get() = Brand700
            val disabled: Color get() = Brand100
        }

        object Text {
            val primaryOn: Color get() = Brand50
            val secondaryOn: Color get() = Brand600
            val disabledOn: Color get() = Brand300
        }

        object Icon {
            val primaryOn: Color get() = Brand50
            val secondaryOn: Color get() = Brand600
            val disabledOn: Color get() = Brand300
        }
    }
}
