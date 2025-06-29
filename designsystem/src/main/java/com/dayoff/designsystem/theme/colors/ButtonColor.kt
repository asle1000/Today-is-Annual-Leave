import androidx.compose.ui.graphics.Color

object ButtonColor {
    val brand = Brand

    object Brand {
        val background = Background
        val text = Text
        val icon = Icon

        object  Background {
            val primary: Color get() = Color(0xFF274337)
            val secondary: Color get() = Color(0xFF3D956A)
            val disabled: Color get() = Color(0xFF6BD0BB)
        }

        object Text {
            val primaryOn: Color get() = Color(0xFFE8F8F0)
            val secondaryOn: Color get() = Color(0xFF4BB27D)
            val disabledOn: Color get() = Color(0xFF83DCB0)
        }

        object Icon {
            val primaryOn: Color get() = Color(0xFFE8F8F0)
            val secondaryOn: Color get() = Color(0xFF4BB27D)
            val disabledOn: Color get() = Color(0xFF83DCB0)
        }
    }
}
