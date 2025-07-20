package com.dayoff.data.datasource

import com.dayoff.core.model.calendar.CalendarEventDto
import com.dayoff.data.mapper.CalendarEventMapper
import com.dayoff.core.db.CalendarEventDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CalendarEventLocalDatasource(
    private val calendarEventDao: CalendarEventDao
) {
    suspend fun saveYearEvents(year: Int, yearEvents: List<CalendarEventDto>) = withContext(Dispatchers.IO) {
        calendarEventDao.deleteByYear(year)

        yearEvents.forEach { dto ->
            calendarEventDao.insertAll(CalendarEventMapper.toEntities(dto))
        }
    }

    suspend fun getYearEvents(year: Int): List<CalendarEventDto> = withContext(Dispatchers.IO) {
        val entities = calendarEventDao.getEventsByYear(year)
        if (entities.isEmpty()) emptyList()
        else entities.groupBy { it.month }.map { (month, events) ->
            CalendarEventMapper.toDto(year, events)
        }
    }

    suspend fun updateYearEvents(year: Int, newEvents: List<CalendarEventDto>) = withContext(Dispatchers.IO) {
        val newEntities = newEvents.flatMap { CalendarEventMapper.toEntities(it) }
        calendarEventDao.deleteByYear(year)
        calendarEventDao.insertAll(newEntities)
    }
} 