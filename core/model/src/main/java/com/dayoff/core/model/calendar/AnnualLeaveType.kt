package com.dayoff.core.model.calendar

/**
 *  Created by KyunghyunPark at 2025. 8. 9.

 */
enum class AnnualLeaveType(val label: String) {
    FULL(label = "연차"),
    HALF(label = "반차"),
    HOURLY(label = "시간차");
}