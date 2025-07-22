package com.dayoff.core.db.model

/**
 *  Created by KyunghyunPark at 2025. 7. 21.

 */
enum class CalendarEventType {
    REST_HOLIDAYS,
    SUBSTITUTE_HOLIDAY,
    ANNIVERSARIES,
    DIVISIONS_24,
    SUNDRY_DAYS;

    companion object {
        fun from(value: String): CalendarEventType = try {
            valueOf(value.uppercase())
        } catch (e: IllegalArgumentException) {
            SUNDRY_DAYS
        }
    }
}