package com.dayoff.data.datasource

import com.dayoff.core.db.dao.CalendarEventDao
import com.dayoff.core.db.entity.CalendarEventEntity
import com.dayoff.core.network.model.CalendarEventDto
import com.dayoff.data.mapper.CalendarEventMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CalendarEventLocalDatasource(
    private val calendarEventDao: CalendarEventDao
) {
    suspend fun updateCalendarEventsFromYear(year: Int, yearEvents: List<CalendarEventDto>) =
        withContext(Dispatchers.IO) {
            calendarEventDao.updateYearEvents(
                year = year,
                newEvents = yearEvents.flatMap { CalendarEventMapper.toEntities(it) },
            )
        }

    fun getCalendarEventsByYear(year: Int): Flow<List<CalendarEventEntity>>  {
        return calendarEventDao.getEventsByYear(year)
    }
} 