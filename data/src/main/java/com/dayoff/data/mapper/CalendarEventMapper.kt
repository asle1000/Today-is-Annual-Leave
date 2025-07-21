package com.dayoff.data.mapper

import com.dayoff.core.db.entity.CalendarEventEntity
import com.dayoff.core.db.model.CalendarEventType
import com.dayoff.core.model.calendar.CalendarDay
import com.dayoff.core.model.calendar.DayCellIndicatorType
import com.dayoff.core.model.calendar.DayCellType
import com.dayoff.core.model.calendar.DayOfWeek
import com.dayoff.core.model.calendar.MonthType
import com.dayoff.core.network.model.CalendarEventDto
import com.dayoff.core.network.model.SpecialDay
import java.time.LocalDate
import java.time.YearMonth

object CalendarEventMapper {
    fun toEntities(dto: CalendarEventDto): List<CalendarEventEntity> = dto.specialDays.map {
        val (year, month, day) = parseDate(it.date)
        CalendarEventEntity(
            name = it.name,
            year = year,
            month = month,
            day = day,
            type = CalendarEventType.from(value = it.type),
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
                type = it.type.name,
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
    private fun CalendarEventEntity.toIndicatorType(): DayCellIndicatorType {
        return when (type) {
            CalendarEventType.REST_HOLIDAYS -> DayCellIndicatorType.HOLIDAY
            CalendarEventType.SUBSTITUTE_HOLIDAY -> DayCellIndicatorType.SUBSTITUTE_HOLIDAY
            CalendarEventType.SUNDRY_DAYS ->
                if (isHoliday) DayCellIndicatorType.SUBSTITUTE_HOLIDAY else DayCellIndicatorType.NONE
            else -> DayCellIndicatorType.NONE
        }
    }

    fun mapEntitiesToCalendarDays(
        year: Int,
        month: Int,
        today: LocalDate = LocalDate.now(),
        startDayOfWeek: DayOfWeek,
        eventEntities: List<CalendarEventEntity>
    ): List<CalendarDay> {
        val yearMonth = YearMonth.of(year, month)

        fun resolveIndicator(date: LocalDate): DayCellIndicatorType {
            return eventEntities
                .firstOrNull { it.year == date.year && it.month == date.monthValue && it.day == date.dayOfMonth }
                ?.toIndicatorType() ?: DayCellIndicatorType.NONE
        }

        return generateCalendarDays(
            yearMonth = yearMonth,
            today = today,
            startDayOfWeek = startDayOfWeek,
            indicatorResolver = ::resolveIndicator
        )
    }

    private fun generateCalendarDays(
        yearMonth: YearMonth,
        today: LocalDate,
        startDayOfWeek: DayOfWeek,
        indicatorResolver: (LocalDate) -> DayCellIndicatorType
    ): List<CalendarDay> {
        val firstDayOfMonth = yearMonth.atDay(1)
        val lastDayOfMonth = yearMonth.atEndOfMonth()
        val offset = ((firstDayOfMonth.dayOfWeek.value - startDayOfWeek.ordinal + 7) % 7)

        val days = mutableListOf<CalendarDay>()

        val prevMonth = yearMonth.minusMonths(1)
        val prevMonthLastDay = prevMonth.atEndOfMonth().dayOfMonth
        repeat(offset) { i ->
            days.add(
                CalendarDay(
                    day = prevMonthLastDay - offset + 1 + i,
                    monthType = MonthType.PREV,
                    cellType = DayCellType.DISABLED,
                    indicatorType = DayCellIndicatorType.NONE
                )
            )
        }

        for (day in 1..lastDayOfMonth.dayOfMonth) {
            val date = yearMonth.atDay(day)
            days.add(
                CalendarDay(
                    day = day,
                    monthType = MonthType.CURRENT,
                    cellType = if (date == today) DayCellType.TODAY else DayCellType.ENABLED,
                    indicatorType = indicatorResolver(date)
                )
            )
        }

        val remaining = (7 - days.size % 7).let { if (it == 7) 0 else it }
        for (day in 1..remaining) {
            days.add(
                CalendarDay(
                    day = day,
                    monthType = MonthType.NEXT,
                    cellType = DayCellType.DISABLED,
                    indicatorType = DayCellIndicatorType.NONE
                )
            )
        }

        return days
    }
} 