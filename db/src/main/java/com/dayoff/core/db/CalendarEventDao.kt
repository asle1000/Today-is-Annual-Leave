package com.dayoff.core.db

import androidx.room.*
import com.dayoff.core.db.entity.CalendarEventEntity

@Dao
interface CalendarEventDao {
    @Query("SELECT * FROM calendar_event WHERE year = :year")
    suspend fun getEventsByYear(year: Int): List<CalendarEventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(events: List<CalendarEventEntity>)

    @Query("DELETE FROM calendar_event WHERE year = :year")
    suspend fun deleteByYear(year: Int)

    @Update
    suspend fun updateEvents(events: List<CalendarEventEntity>)
} 