package com.dayoff.core.db.dao

import androidx.room.*
import com.dayoff.core.db.entity.CalendarEventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CalendarEventDao {
    @Query("SELECT * FROM calendar_event WHERE year = :year")
    fun getEventsByYear(year: Int): Flow<List<CalendarEventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(events: List<CalendarEventEntity>)

    @Query("DELETE FROM calendar_event WHERE year = :year")
    suspend fun deleteByYear(year: Int)

    @Update
    suspend fun updateEvents(events: List<CalendarEventEntity>)

    @Transaction
    suspend fun updateYearEvents(year: Int, newEvents: List<CalendarEventEntity>) {
        deleteByYear(year = year)
        insertAll(events = newEvents)
    }
} 