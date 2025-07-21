package com.dayoff.data.datasource

import com.dayoff.core.network.model.CalendarEventDto

/**
 *  Created by KyunghyunPark at 2025. 7. 13.

 */
interface CalendarEventRemoteDataSource {
    suspend fun fetchCalendarEvent(year: Int): List<CalendarEventDto>
} 