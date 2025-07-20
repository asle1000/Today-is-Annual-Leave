package com.dayoff.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dayoff.core.db.entity.CalendarEventEntity

@Database(
    entities = [CalendarEventEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TialDatabase : RoomDatabase() {
    abstract fun calendarEventDao(): CalendarEventDao
} 