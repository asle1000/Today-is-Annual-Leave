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
        val primary: Color get() = Color(0xFFFFFFFF)
    }

    object Brand {
        val primary: Color get() = Color(0xFF60D194)
        val secondary: Color get() = Color(0xFF6BD0BB)
        val tertiary: Color get() = Color(0xFFE8F8F0)
        val overlay: Color get() = Color(0xFFCBF0DB).copy(0.4f) // 40%
    }

    object Surface {
        val primary: Color get() = Color(0xFFF8F8FC)
        val secondary: Color get() = Color(0xFFFFF1F9)
        val tertiary: Color get() = Color(0xFFE2E8F0)
    }

    object Disabled {
        val primary: Color get() = Color(0xFFCBD5E1)
    }

    object Success {
        val primary: Color get() = Color(0xFFE6FFF1)
    }

    object Warning {
        val primary: Color get() = Color(0xFFFFFEEB)
    }

    object Danger {
        val primary: Color get() = Color(0xFFFFE1E2)
    }

    object Utilities {
        val dimmed: Color get() = Color(0xFF000000).copy(alpha = 0.4f) // 40%
        val toast: Color get() = Color(0xFF000000).copy(alpha = 0.8f) // 80%
        val loading: Color get() = Color(0xFFFFFFFF).copy(alpha = 0.6f) // 60%
        val tooltip: Color get() = Color(0xFF274337)
    }
}