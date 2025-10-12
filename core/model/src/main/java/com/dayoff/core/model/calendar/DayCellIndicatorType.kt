package com.dayoff.core.model.calendar

/**
 *  Created by KyunghyunPark at 2025. 7. 6.

 */
enum class DayCellIndicatorType(val label: String) {
    NONE(""),
    HOLIDAY("휴일"),
    SUBSTITUTE_HOLIDAY("대체 휴일"),
    ANNUAL_LEAVE_RECOMMEND("추천 연차"),
    ANNUAL_LEAVE("연차 사용"),;
}