package com.dayoff.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dayoff.core.db.converter.AnnualLeaveTypeConverters
import com.dayoff.core.db.converter.CalendarEventTypeConverter
import com.dayoff.core.db.converter.HashtagConverters
import com.dayoff.core.db.dao.AnnualLeaveRecordDao
import com.dayoff.core.db.dao.CalendarEventDao
import com.dayoff.core.db.dao.HashtagDao
import com.dayoff.core.db.dao.YearManagementDao
import com.dayoff.core.db.entity.AnnualLeaveRecordEntity
import com.dayoff.core.db.entity.CalendarEventEntity
import com.dayoff.core.db.entity.HashtagEntity
import com.dayoff.core.db.entity.YearManagementEntity

@Database(
    entities = [CalendarEventEntity::class, YearManagementEntity::class, AnnualLeaveRecordEntity::class, HashtagEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(CalendarEventTypeConverter::class, AnnualLeaveTypeConverters::class, HashtagConverters::class)
abstract class TialDatabase : RoomDatabase() {
    abstract fun calendarEventDao(): CalendarEventDao
    abstract fun yearManagementDao(): YearManagementDao
    abstract fun annualLeaveRecordDao(): AnnualLeaveRecordDao
    abstract fun hashtagDao(): HashtagDao
}