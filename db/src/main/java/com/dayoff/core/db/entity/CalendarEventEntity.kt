package com.dayoff.core.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import com.dayoff.core.db.model.CalendarEventType

@Entity(tableName = "calendar_event")
data class CalendarEventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "year")
    val year: Int,
    @ColumnInfo(name = "month")
    val month: Int,
    @ColumnInfo(name = "day")
    val day: Int,
    @ColumnInfo(name = "type")
    val type: CalendarEventType,
    @ColumnInfo(name = "is_holiday")
    val isHoliday: Boolean
) 