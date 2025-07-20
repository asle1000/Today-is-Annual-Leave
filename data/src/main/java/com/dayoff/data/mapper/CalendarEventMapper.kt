package com.dayoff.data.mapper

import com.dayoff.core.db.entity.CalendarEventEntity
import com.dayoff.core.model.calendar.CalendarEventDto
import com.dayoff.core.model.calendar.SpecialDay

object CalendarEventMapper {
    fun toEntities(dto: CalendarEventDto): List<CalendarEventEntity> =
        dto.specialDays.map {
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

    fun toDto(year: Int, entities: List<CalendarEventEntity>): CalendarEventDto =
        CalendarEventDto(
            month = if (entities.isNotEmpty()) "%04d%02d".format(year, entities[0].month) else year.toString(),
            specialDays = entities.map {
                SpecialDay(
                    name = it.name,
                    date = "%04d%02d%02d".format(it.year, it.month, it.day),
                    type = it.type,
                    isHoliday = it.isHoliday
                )
            }
        )

    private fun parseDate(date: String): Triple<Int, Int, Int> {
        val year = date.substring(0, 4).toInt()
        val month = date.substring(4, 6).toInt()
        val day = date.substring(6, 8).toInt()
        return Triple(year, month, day)
    }
} 