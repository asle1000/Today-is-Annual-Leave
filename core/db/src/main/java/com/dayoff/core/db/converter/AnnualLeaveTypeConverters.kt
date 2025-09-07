package com.dayoff.core.db.converter

import androidx.room.TypeConverter
import com.dayoff.core.model.calendar.AnnualLeaveType

/**
 *  Created by KyunghyunPark at 2025. 8. 30.

 */
class AnnualLeaveTypeConverters {
    @TypeConverter
    fun fromType(type: AnnualLeaveType): String = type.name

    @TypeConverter
    fun toType(value: String): AnnualLeaveType = AnnualLeaveType.valueOf(value = value)
}