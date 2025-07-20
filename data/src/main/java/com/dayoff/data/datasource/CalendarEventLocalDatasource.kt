package com.dayoff.data.datasource

import com.dayoff.core.model.calendar.CalendarEventDto
import com.dayoff.data.mapper.CalendarEventMapper
import com.dayoff.core.db.CalendarEventDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CalendarEventLocalDatasource(
    private val calendarEventDao: CalendarEventDao
) {
    suspend fun saveYearEvents(year: Int, yearEvents: List<CalendarEventDto>) =
        withContext(Dispatchers.IO) {
            calendarEventDao.updateYearEvents(
                year = year,
                newEvents = yearEvents.flatMap { CalendarEventMapper.toEntities(it) },
            )
        }

    suspend fun getYearEvents(year: Int): List<CalendarEventDto> = withContext(Dispatchers.IO) {
        val entities = calendarEventDao.getEventsByYear(year)
        entities.groupBy { it.month }.map { (_, events) ->
            CalendarEventMapper.toDto(year, events)
        }
    }
} 