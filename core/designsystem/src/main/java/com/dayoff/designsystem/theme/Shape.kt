package com.dayoff.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

/**
 *  Created by KyunghyunPark at 2025. 7. 1.

 */
@Suppress("unused")
object Shape {

    val None = RoundedCornerShape(0.dp)

    val ExtraSmall = RoundedCornerShape(4.dp)
    val Small = RoundedCornerShape(8.dp)
    val Medium = RoundedCornerShape(12.dp)
    val Large = RoundedCornerShape(16.dp)

    val LargeIncreased = RoundedCornerShape(20.dp)
    val ExtraLarge = RoundedCornerShape(28.dp)
    val ExtraLargeIncreased = RoundedCornerShape(32.dp)
    val ExtraExtraLarge = RoundedCornerShape(48.dp)

    val Full = RoundedCornerShape(1000.dp)
}

val LocalTialShapes = staticCompositionLocalOf {
    Shape
}