package com.dayoff.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dayoff.core.db.converter.CalendarEventTypeConverter
import com.dayoff.core.db.entity.CalendarEventEntity

@Database(
    entities = [CalendarEventEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(CalendarEventTypeConverter::class)
abstract class TialDatabase : RoomDatabase() {
    abstract fun calendarEventDao(): CalendarEventDao
} 