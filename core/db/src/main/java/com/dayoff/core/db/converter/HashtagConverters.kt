package com.dayoff.core.db.converter

import HashtagCategory
import androidx.room.TypeConverter

class HashtagConverters {
    @TypeConverter
    fun fromCategory(category: HashtagCategory): String {
        return category.label
    }

    @TypeConverter
    fun toCategory(label: String): HashtagCategory {
        return HashtagCategory.fromLabel(label)
    }
}