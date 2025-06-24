package com.dayoff.calendar.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dayoff.calendar.ui.model.DayInfo
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.TemporalAdjusters

/**
 * 월 단위 캘린더
 *
 * @param yearMonth 년월
 * @param selectedDate 선택된 날짜
 * @param onDateSelected 날짜 선택 콜백
 * @param startDayOfWeek 시작 요일
 * @param holidays 휴일 목록
 * @param alternativeHolidays 대체 휴일 목록
 * @param annualLeaves 연차 목록
 * @param recommendedDays 추천 날짜 목록
 * @param modifier Modifier
 */
@Composable
fun MonthCalendar(
    yearMonth: YearMonth,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    startDayOfWeek: DayOfWeek = DayOfWeek.MONDAY,
    holidays: List<LocalDate> = emptyList(),
    alternativeHolidays: List<LocalDate> = emptyList(),
    annualLeaves: List<LocalDate> = emptyList(),
    recommendedDays: List<LocalDate> = emptyList(),
    modifier: Modifier = Modifier
) {
    val days = remember(yearMonth, startDayOfWeek) {
        generateDaysForMonth(yearMonth, startDayOfWeek)
    }

    Column(modifier = modifier) {
        DayOfWeekHeader(startDayOfWeek = startDayOfWeek)
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(days) { date ->
                val dayInfo = createDayInfo(
                    date = date,
                    holidays = holidays,
                    alternativeHolidays = alternativeHolidays,
                    annualLeaves = annualLeaves,
                    recommendedDays = recommendedDays
                )
                
                DayCell(
                    dayInfo = dayInfo,
                    yearMonth = yearMonth,
                    isSelected = date == selectedDate,
                    onClick = { onDateSelected(date) }
                )
            }
        }
    }
}

@Composable
private fun DayOfWeekHeader(
    startDayOfWeek: DayOfWeek,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val days = remember(startDayOfWeek) {
            generateDayOfWeekHeaders(startDayOfWeek)
        }
        days.forEach { day ->
            Text(
                text = day,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}

private fun createDayInfo(
    date: LocalDate,
    holidays: List<LocalDate>,
    alternativeHolidays: List<LocalDate>,
    annualLeaves: List<LocalDate>,
    recommendedDays: List<LocalDate>
): DayInfo {
    val isAnnualLeave = date in annualLeaves
    val prevDay = date.minusDays(1)
    val nextDay = date.plusDays(1)
    
    return DayInfo(
        date = date,
        isHoliday = date in holidays,
        isAlternativeHoliday = date in alternativeHolidays,
        isAnnualLeave = isAnnualLeave,
        isAnnualLeaveStart = isAnnualLeave && prevDay !in annualLeaves,
        isAnnualLeaveEnd = isAnnualLeave && nextDay !in annualLeaves,
        isAnnualLeaveSingle = isAnnualLeave && prevDay !in annualLeaves && nextDay !in annualLeaves,
        isRecommendedDay = date in recommendedDays
    )
}

private fun generateDayOfWeekHeaders(startDayOfWeek: DayOfWeek): List<String> {
    val days = listOf("월", "화", "수", "목", "금", "토", "일")
    val startIndex = when (startDayOfWeek) {
        DayOfWeek.MONDAY -> 0
        DayOfWeek.SUNDAY -> 6
        else -> 0
    }
    return days.subList(startIndex, days.size) + days.subList(0, startIndex)
}

private fun generateDaysForMonth(yearMonth: YearMonth, startDayOfWeek: DayOfWeek): List<LocalDate> {
    val firstOfMonth = yearMonth.atDay(1)
    val lastOfMonth = yearMonth.atEndOfMonth()
    
    val firstDayOfCalendar = when (startDayOfWeek) {
        DayOfWeek.MONDAY -> firstOfMonth.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        DayOfWeek.SUNDAY -> firstOfMonth.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
        else -> firstOfMonth.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    }
    
    val lastDayOfCalendar = lastOfMonth.with(TemporalAdjusters.nextOrSame(
        when (startDayOfWeek) {
            DayOfWeek.MONDAY -> DayOfWeek.SUNDAY
            DayOfWeek.SUNDAY -> DayOfWeek.SATURDAY
            else -> DayOfWeek.SUNDAY
        }
    ))

    return generateSequence(firstDayOfCalendar) { date ->
        if (date.isBefore(lastDayOfCalendar)) date.plusDays(1) else null
    }.toList()
}

@Preview(showBackground = true)
@Composable
private fun MonthCalendarPreview() {
    Box(
        modifier = Modifier.padding(16.dp)
    ) {
        MonthCalendar(
            yearMonth = YearMonth.now(),
            selectedDate = null,
            onDateSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MonthCalendarSundayStartPreview() {
    Box(
        modifier = Modifier.padding(16.dp)
    ) {
        MonthCalendar(
            yearMonth = YearMonth.now(),
            selectedDate = null,
            onDateSelected = {},
            startDayOfWeek = DayOfWeek.SUNDAY
        )
    }
} 