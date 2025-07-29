package com.dayoff.data.repository

import com.dayoff.core.model.calendar.CalendarDay
import com.dayoff.core.network.model.CalendarEventDto
import kotlinx.coroutines.flow.Flow

interface CalendarEventRepository {
    suspend fun fetchCalendarEvents(year: Int): List<CalendarEventDto>
    fun getCalendarEventsByYear(year: Int, month: Int): Flow<List<CalendarDay>>
}