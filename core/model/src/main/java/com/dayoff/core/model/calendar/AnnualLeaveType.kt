package com.dayoff.core.model.calendar

/**
 *  Created by KyunghyunPark at 2025. 8. 9.

 */
enum class AnnualLeaveType(val label: String) {
    FULL(label = "연차"),
    HALF_AM(label = "오전 반차"),
    HALF_FM(label = "오후 반차"),
    HOURLY(label = "시간차");
}