package com.dayoff.designsystem.model

/**
 *  Created by KyunghyunPark at 2025. 7. 6.

 */
enum class DayOfWeek(val label: String) {
    MONDAY("월"),
    TUESDAY("화"),
    WEDNESDAY("수"),
    THURSDAY("목"),
    FRIDAY("금"),
    SATURDAY("토"),
    SUNDAY("일");

    companion object {
        fun getOrderedDays(start: DayOfWeek): List<DayOfWeek> {
            val all = DayOfWeek.entries
            val startIdx = all.indexOf(start)
            return all.drop(startIdx) + all.take(startIdx)
        }
    }
}