package com.dayoff.data.mapper

import com.dayoff.core.db.entity.CalendarEventEntity
import com.dayoff.core.model.calendar.CalendarEventDto
import com.dayoff.core.model.calendar.SpecialDay

object CalendarEventMapper {
    fun toEntities(dto: CalendarEventDto): List<CalendarEventEntity> = dto.specialDays.map {
        val (year, month, day) = parseDate(it.date)
        CalendarEventEntity(
            name = it.name,
            year = year,
            month = month,
            day = day,
            type = it.type,
            isHoliday = it.isHoliday
        )
    }

    fun toDto(year: Int, entities: List<CalendarEventEntity>): CalendarEventDto = CalendarEventDto(
        month = if (entities.isNotEmpty()) "%04d%02d".format(
            year, entities.first().month
        ) else "%04d".format(year), specialDays = entities.map {
            SpecialDay(
                name = it.name,
                date = "%04d%02d%02d".format(it.year, it.month, it.day),
                type = it.type,
                isHoliday = it.isHoliday
            )
        })

    private fun parseDate(date: String): Triple<Int, Int, Int> {
        require(date.length == 8) { "Date string must be 8 characters long (yyyyMMdd format)" }
        require(date.all { it.isDigit() }) { "Date string must contain only digits" }

        val year = date.substring(0, 4).toInt()
        val month = date.substring(4, 6).toInt()
        val day = date.substring(6, 8).toInt()

        require(month in 1..12) { "Month must be between 1 and 12" }
        require(day in 1..31) { "Day must be between 1 and 31" }

        return Triple(year, month, day)
    }
} 