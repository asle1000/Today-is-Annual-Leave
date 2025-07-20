package com.dayoff.data.repository

import com.dayoff.data.datasource.CalendarEventLocalDatasource
import com.dayoff.data.datasource.CalendarEventRemoteDataSource
import com.dayoff.core.model.calendar.CalendarEventDto
import timber.log.Timber

class CalendarRepository(
    private val calendarEventRemoteDataSource: CalendarEventRemoteDataSource,
    private val calendarEventLocalDatasource: CalendarEventLocalDatasource
) {
    /**
     * Fetch remote month days
     *
     * @param year
     * @return
     */
    suspend fun fetchCalendarEvents(year: Int): List<CalendarEventDto> {
        val response = calendarEventRemoteDataSource.fetchCalendarEvent(year)
        calendarEventLocalDatasource.saveYearEvents(year = year, yearEvents = response)
        Timber.d("fetchCalendarEvents: \n${response.joinToString("\n")}")
        return response
    }
} 