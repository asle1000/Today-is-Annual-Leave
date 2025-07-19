package com.dayoff.data.datasource

import com.dayoff.core.network.api.CalendarApi
import com.dayoff.core.model.calendar.CalendarEventDto

class CalendarEventRemoteDataSourceImpl(
    private val calendarApi: CalendarApi
): CalendarEventRemoteDataSource {
    override suspend fun fetchCalendarEvent(year: Int): List<CalendarEventDto> {
        return calendarApi.fetchCalendarEventList(year)
    }
} 