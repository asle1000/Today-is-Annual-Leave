package com.dayoff.core.ui.utils

/**
 *  Created by KyunghyunPark at 2025. 8. 16.

 */
import com.dayoff.core.model.calendar.MonthType
import com.dayoff.core.ui.model.Selection
import java.time.LocalDate
import java.time.YearMonth

object CalendarDateUtil {

    /** 유효한 날짜 반환 */
    fun getValidDateOrNull(baseMonth: YearMonth, day: Int, monthType: MonthType): LocalDate? {
        if (day <= 0) return null
        val targetMonth = when (monthType) {
            MonthType.CURRENT  -> baseMonth
            MonthType.PREVIOUS -> baseMonth.minusMonths(1)
            MonthType.NEXT     -> baseMonth.plusMonths(1)
        }
        return if (targetMonth.isValidDay(day)) targetMonth.atDay(day) else null
    }

    /** 두 날짜 사이 모든 날짜 반환 */
    fun getDateRange(start: LocalDate, end: LocalDate): List<LocalDate> {
        val (s, e) = if (start <= end) start to end else end to start
        val result = ArrayList<LocalDate>((e.toEpochDay() - s.toEpochDay() + 1).toInt())
        var current = s
        while (!current.isAfter(e)) {
            result += current
            current = current.plusDays(1)
        }
        return result
    }

    /** 탭 후 선택 상태 계산 */
    fun calculateNextSelection(current: Selection, date: LocalDate): Selection {
        val selectedDates = current.selectedDates
        val anchor = current.rangeStart

        return when {
            anchor == null && selectedDates.isEmpty() -> Selection(date, setOf(date))
            anchor == null && selectedDates.size > 1 -> Selection(date, setOf(date))
            anchor == null && selectedDates.size == 1 -> {
                val onlyDate = selectedDates.first()
                when {
                    date == onlyDate -> Selection(null, emptySet())
                    date.isAfter(onlyDate) -> Selection(null, getDateRange(onlyDate, date).toSet())
                    else -> Selection(date, setOf(date))
                }
            }
            anchor != null -> when {
                date == anchor -> Selection(null, emptySet())
                date.isAfter(anchor) -> Selection(null, getDateRange(anchor, date).toSet())
                else -> Selection(date, setOf(date))
            }
            else -> Selection(date, setOf(date))
        }
    }
}