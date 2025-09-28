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
            CalendarEventType.ANNUAL_LEAVE -> DayCellIndicatorType.ANNUAL_LEAVE
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

        val eventsByDate: Map<LocalDate, List<CalendarEventEntity>> =
            eventEntities.groupBy { LocalDate.of(it.year, it.month, it.day) }

        fun resolveDayName(date: LocalDate): String =
            eventsByDate[date]?.firstOrNull()?.name ?: ""

        fun resolveIndicatorList(date: LocalDate): List<DayCellIndicatorType> {
            return eventsByDate[date]
                ?.asSequence()
                ?.map { it.toIndicatorType() }
                ?.filter { it != DayCellIndicatorType.NONE }
                ?.distinct()
                ?.sortedBy { indicatorPriority(it) }
                ?.toList()
                .orEmpty()
        }

        return generateCalendarDays(
            yearMonth = yearMonth,
            today = today,
            startDayOfWeek = startDayOfWeek,
            dayNameResolver = ::resolveDayName,
            indicatorResolver = ::resolveIndicatorList
        )
    }

    private fun indicatorPriority(type: DayCellIndicatorType): Int = when (type) {
        DayCellIndicatorType.HOLIDAY -> 0
        DayCellIndicatorType.SUBSTITUTE_HOLIDAY -> 1
        DayCellIndicatorType.ANNUAL_LEAVE -> 2
        else -> 3
    }

    private fun generateCalendarDays(
        yearMonth: YearMonth,
        today: LocalDate,
        startDayOfWeek: DayOfWeek,
        dayNameResolver: (LocalDate) -> String,
        indicatorResolver: (LocalDate) -> List<DayCellIndicatorType>
    ): List<CalendarDay> {
        val firstDayOfMonth = yearMonth.atDay(1)
        val lastDayOfMonth = yearMonth.atEndOfMonth()

        val firstDow = firstDayOfMonth.dayOfWeek.value
        val startDow = if(startDayOfWeek == DayOfWeek.MONDAY) 1 else startDayOfWeek.ordinal
        val offset = (firstDow - startDow + 7) % 7

        val days = mutableListOf<CalendarDay>()

        val prevMonth = yearMonth.minusMonths(1)
        val prevMonthLastDay = prevMonth.atEndOfMonth().dayOfMonth
        repeat(offset) { i ->
            val dayOfPrev = prevMonthLastDay - offset + 1 + i
            val date = prevMonth.atDay(dayOfPrev)
            days += CalendarDay(
                day = date.dayOfMonth,
                name = dayNameResolver(date),
                monthType = MonthType.PREVIOUS,
                cellType = DayCellType.DISABLED,
                indicatorType = emptyList()
            )
        }

        for (d in 1..lastDayOfMonth.dayOfMonth) {
            val date = yearMonth.atDay(d)
            days += CalendarDay(
                day = date.dayOfMonth,
                name = dayNameResolver(date),
                monthType = MonthType.CURRENT,
                cellType = if (date == today) DayCellType.TODAY else DayCellType.ENABLED,
                indicatorType = indicatorResolver(date)
            )
        }

        val remainder = days.size % 7
        val padToFullWeek = if (remainder == 0) 0 else 7 - remainder

        repeat(padToFullWeek) { i ->
            val date = lastDayOfMonth.plusDays((i + 1).toLong())
            days += CalendarDay(
                day = date.dayOfMonth,
                name = dayNameResolver(date),
                monthType = MonthType.NEXT,
                cellType = DayCellType.DISABLED,
                indicatorType = emptyList()
            )
        }

        val totalCells = 42
        if (days.size < totalCells) {
            val need = totalCells - days.size
            val startPlus = padToFullWeek + 1
            repeat(need) { i ->
                val date = lastDayOfMonth.plusDays((startPlus + i).toLong())
                days += CalendarDay(
                    day = date.dayOfMonth,
                    name = dayNameResolver(date),
                    monthType = MonthType.NEXT,
                    cellType = DayCellType.DISABLED,
                    indicatorType = emptyList()
                )
            }
        }

        return days
    }
} 