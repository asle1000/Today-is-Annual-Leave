import androidx.compose.ui.graphics.Color

object BorderColor {
    val base = object {
        val input: Color get() = Color(0xFFE2E8F0)
    }
    val surface = object {
        val primary: Color get() = Color(0xFFFFF1F9)
        val secondary: Color get() = Color(0xFFE2E8F0)
        val tertiary: Color get() = Color(0xFFCBD5E1)
    }
    val brand = object {
        val primary: Color get() = Color(0xFF60D194)
        val secondary: Color get() = Color(0xFF83DCB0)
        val tertiary: Color get() = Color(0xFFE8F8F0)
    }
    val disabled = object {
        val primary: Color get() = Color(0xFFE2E8F0)
    }
    val success = object {
        val primary: Color get() = Color(0xFF3B8216)
    }
    val warning = object {
        val primary: Color get() = Color(0xFFF59E0B)
    }
    val danger = object {
        val primary: Color get() = Color(0xFFEF4444)
    }
}