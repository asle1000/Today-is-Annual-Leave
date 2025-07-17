package com.dayoff.data.datasource

/**
 *  Created by KyunghyunPark at 2025. 7. 13.

 */
interface CalendarEventRemoteDataSource {
    suspend fun fetchCalendarEvent()
}