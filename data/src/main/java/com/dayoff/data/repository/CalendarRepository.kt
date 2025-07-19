package com.dayoff.data.repository

import com.dayoff.data.datasource.CalendarEventLocalDatasource
import com.dayoff.data.datasource.CalendarEventRemoteDataSource
import com.dayoff.core.model.calendar.CalendarEventDto

class CalendarRepository(
    private val remote: CalendarEventRemoteDataSource,
    private val local: CalendarEventLocalDatasource
) {
    /**
     * Fetch remote month days
     *
     * @param year
     * @return
     */
    suspend fun fetchCalendarEvents(year: Int): List<CalendarEventDto> {
        return remote.fetchCalendarEvent(year)
    }
} 