package com.dayoff.data.repository

import com.dayoff.core.model.calendar.CalendarDay
import com.dayoff.core.model.calendar.DayOfWeek
import com.dayoff.core.network.model.CalendarEventDto
import com.dayoff.data.datasource.CalendarEventLocalDatasource
import com.dayoff.data.datasource.CalendarEventRemoteDataSource
import com.dayoff.data.mapper.CalendarEventMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import timber.log.Timber

class CalendarRepository(
    private val calendarEventRemoteDataSource: CalendarEventRemoteDataSource,
    private val calendarEventLocalDatasource: CalendarEventLocalDatasource,
) {
    /**
     * TODO Error handling
     * Fetch remote month days
     *
     * @param year
     * @return
     */
    suspend fun fetchCalendarEvents(year: Int): List<CalendarEventDto> {
        val response = calendarEventRemoteDataSource.fetchCalendarEvent(year = year)
        calendarEventLocalDatasource.updateCalendarEventsFromYear(year = year, yearEvents = response)
        Timber.d("fetchCalendarEvents: \n${response.joinToString("\n")}")
        return response
    }

     @OptIn(ExperimentalCoroutinesApi::class)
     fun getCalendarEventsByYear(year: Int, month: Int): Flow<List<CalendarDay>> {
        return calendarEventLocalDatasource.getCalendarEventsByYear(year = year).map {
             return@map CalendarEventMapper.mapEntitiesToCalendarDays(
                year = year,
                month = month,
                eventEntities = it,
                startDayOfWeek = DayOfWeek.MONDAY
            )
        }
    }
} 