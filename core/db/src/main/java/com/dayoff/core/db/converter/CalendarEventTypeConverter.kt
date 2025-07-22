package com.dayoff.core.db.converter

import androidx.room.TypeConverter
import com.dayoff.core.db.model.CalendarEventType

class CalendarEventTypeConverter {
    @TypeConverter
    fun fromType(type: CalendarEventType): String = type.name

    @TypeConverter
    fun toType(value: String): CalendarEventType = CalendarEventType.from(value)
}