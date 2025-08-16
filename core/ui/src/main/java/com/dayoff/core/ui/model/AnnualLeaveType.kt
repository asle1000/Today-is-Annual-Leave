package com.dayoff.core.ui.model

import androidx.annotation.DrawableRes
import com.dayoff.core.ui.R

/**
 *  Created by KyunghyunPark at 2025. 8. 9.

 */
enum class AnnualLeaveType(
    val label: String,
    @DrawableRes val iconRes: Int,
) {
    FULL(
        label = "연차",
        iconRes = R.drawable.img_annual_leave,
    ),
    HALF(
        label = "반차",
        iconRes = R.drawable.img_half_annual_leave,
    ),
    HOURLY(
        label = "시간차",
        iconRes = R.drawable.img_particle_day_leave,
    );

    companion object {
        fun toList(): List<Pair<Int, String>> {
            val options = AnnualLeaveType.entries.toTypedArray().map { type ->
                type.iconRes to type.label
            }

            return options
        }
    }
}