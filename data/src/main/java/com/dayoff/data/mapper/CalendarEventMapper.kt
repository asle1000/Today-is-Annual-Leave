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

        // 날짜별 이벤트(겹침 유지)
        val eventsByDate: Map<LocalDate, List<CalendarEventEntity>> =
            eventEntities.groupBy { LocalDate.of(it.year, it.month, it.day) }

        fun resolveDayName(date: LocalDate): String =
            eventsByDate[date]?.firstOrNull()?.name ?: ""

        fun resolveIndicatorList(date: LocalDate): List<DayCellIndicatorType> {
            return eventsByDate[date]
                ?.asSequence()
                ?.map { it.toIndicatorType() }
                ?.filter { it != DayCellIndicatorType.NONE }
                ?.distinct() // 동일 타입 중복 제거
                ?.sortedBy { indicatorPriority(it) } // ← 우선순위 적용
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

    /** 우선순위: 휴일(0) → 대체 휴일(1) → 연차(2) → 기타(3) */
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
        dayNameResolver:  (LocalDate) -> String,
        indicatorResolver: (LocalDate) -> List<DayCellIndicatorType>
    ): List<CalendarDay> {
        val firstDayOfMonth = yearMonth.atDay(1)
        val lastDayOfMonth = yearMonth.atEndOfMonth()
        // ✅ ordinal 대신 value 사용(1=월 … 7=일)

        val offset = ((firstDayOfMonth.dayOfWeek.value - startDayOfWeek.ordinal + 7) % 7)

        val days = mutableListOf<CalendarDay>()

        // 이전 달 패딩
        val previousMonth = yearMonth.minusMonths(1)
        val previousMonthLastDay = previousMonth.atEndOfMonth().dayOfMonth
        repeat(offset) { i ->
            val day = previousMonthLastDay - offset + 1 + i
            val date = previousMonth.atDay(day)
            days += CalendarDay(
                day = day,
                name = "",
                monthType = MonthType.PREVIOUS,
                cellType = DayCellType.DISABLED,
                indicatorType = emptyList()
            )
        }

        // 이번 달
        for (day in 1..lastDayOfMonth.dayOfMonth) {
            val date = yearMonth.atDay(day)
            days += CalendarDay(
                day = day,
                name = dayNameResolver(date),
                monthType = MonthType.CURRENT,
                cellType = if (date == today) DayCellType.TODAY else DayCellType.ENABLED,
                indicatorType = indicatorResolver(date)
            )
        }

        // 다음 달 패딩
        val remaining = (7 - days.size % 7).let { if (it == 7) 0 else it }
        for (day in 1..remaining) {
            val date = lastDayOfMonth.plusDays(day.toLong())
            days += CalendarDay(
                day = day,
                name = "",
                monthType = MonthType.NEXT,
                cellType = DayCellType.DISABLED,
                indicatorType = emptyList()
            )
        }

        return days
    }
} 