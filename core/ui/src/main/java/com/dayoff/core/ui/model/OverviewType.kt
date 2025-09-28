package com.dayoff.core.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.dayoff.core.ui.R

enum class OverviewType(
    @DrawableRes
    val iconResource: Int,
    @StringRes
    val descriptionResource: Int,
) {
    CALENDAR(
        iconResource = R.drawable.ic_calendar,
        descriptionResource = R.string.DESCRIPTION_ICON_CALENDAR,
    ),
    STAMP(
        iconResource = R.drawable.ic_nest_farsight_seasonal,
        descriptionResource = R.string.DESCRIPTION_ICON_STAMP,
    ),
}