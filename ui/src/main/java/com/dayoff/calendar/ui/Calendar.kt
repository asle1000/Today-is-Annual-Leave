package com.dayoff.calendar.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dayoff.calendar.ui.components.CalendarHeader
import com.dayoff.calendar.ui.components.CalendarTopBar
import com.dayoff.calendar.ui.components.MonthCalendar
import java.time.LocalDate
import java.time.YearMonth

/**
 * 연차 및 휴일을 표시하는 캘린더
 *
 * @param modifier Modifier
 * @param onAddClick 추가 버튼 클릭 콜백
 * @param onDateSelected 날짜 선택 콜백
 * @param holidays 휴일 목록
 * @param alternativeHolidays 대체 휴일 목록
 * @param annualLeaves 연차 목록
 * @param forceDarkMode 다크 모드 강제 설정 (true: 다크, false: 라이트, null: 시스템)
 */
@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit = {},
    onDateSelected: (LocalDate) -> Unit = {},
    holidays: List<LocalDate> = emptyList(),
    alternativeHolidays: List<LocalDate> = emptyList(),
    annualLeaves: List<LocalDate> = emptyList(),
    forceDarkMode: Boolean? = null
) {
    val colorScheme = when (forceDarkMode) {
        true -> MaterialTheme.colorScheme.copy(
            background = Color.Black,
            surface = Color.DarkGray,
            onSurface = Color.White,
            primary = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
            secondary = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f),
            tertiary = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.8f),
            tertiaryContainer = Color.DarkGray
        )

        false -> MaterialTheme.colorScheme.copy(
            background = Color.White,
            surface = Color.LightGray,
            onSurface = Color.Black,
            tertiaryContainer = Color.LightGray
        )

        null -> MaterialTheme.colorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme
    ) {
        var currentMonth by remember { mutableStateOf(YearMonth.now()) }
        var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

        Column(
            modifier = modifier
                .background(colorScheme.background)
        ) {
            CalendarTopBar(
                currentYear = currentMonth.year,
                onAddClick = onAddClick,
                onYearChange = { year ->
                    currentMonth = currentMonth.withYear(year)
                }
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .background(
                        color = Color(0xFFF5F5F5),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(vertical = 12.dp, horizontal = 20.dp)
            ) {
                CalendarHeader(
                    currentMonth = currentMonth,
                    onRefresh = { /* TODO: Implement refresh logic */ },
                    onMonthChange = { month ->
                        currentMonth = currentMonth.withMonth(month)
                    }
                )

                MonthCalendar(
                    yearMonth = currentMonth,
                    selectedDate = selectedDate,
                    onDateSelected = { date ->
                        selectedDate = date
                        onDateSelected(date)
                    },
                    holidays = holidays,
                    alternativeHolidays = alternativeHolidays,
                    annualLeaves = annualLeaves
                )

                // 범례
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    LegendItem(
                        color = colorScheme.error,
                        text = "휴일"
                    )
                    LegendItem(
                        color = colorScheme.tertiary,
                        text = "대체 휴일"
                    )
                    LegendItem(
                        color = colorScheme.tertiary.copy(alpha = 0.2f),
                        text = "연차"
                    )
                }
            }

        }
    }
}

/**
 * 캘린더 하단 범례 아이템
 *
 * @param color 색상
 * @param text 텍스트
 * @param modifier Modifier
 */
@Composable
private fun LegendItem(
    color: Color,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

/** 기본 캘린더 프리뷰 */
@Preview(showBackground = true)
@Composable
private fun CalendarPreview() {
    Calendar(
        modifier = Modifier.fillMaxWidth(),
        holidays = listOf(
            LocalDate.now(),
            LocalDate.now().plusDays(7)
        ),
        alternativeHolidays = listOf(
            LocalDate.now().plusDays(1)
        ),
        annualLeaves = listOf(
            LocalDate.now().plusDays(2),
            LocalDate.now().plusDays(3),
            LocalDate.now().plusDays(4)
        ),
        forceDarkMode = null
    )
}

/** 다크 모드 캘린더 프리뷰 */
@Preview(showBackground = true)
@Composable
private fun CalendarDarkPreview() {
    Calendar(
        modifier = Modifier.fillMaxWidth(),
        holidays = listOf(
            LocalDate.now(),
            LocalDate.now().plusDays(7)
        ),
        alternativeHolidays = listOf(
            LocalDate.now().plusDays(1)
        ),
        annualLeaves = listOf(
            LocalDate.now().plusDays(2),
            LocalDate.now().plusDays(3),
            LocalDate.now().plusDays(4)
        ),
        forceDarkMode = true
    )
}

/** 라이트 모드 캘린더 프리뷰 */
@Preview(showBackground = true)
@Composable
private fun CalendarLightPreview() {
    Calendar(
        modifier = Modifier.fillMaxWidth(),
        holidays = listOf(
            LocalDate.now(),
            LocalDate.now().plusDays(7)
        ),
        alternativeHolidays = listOf(
            LocalDate.now().plusDays(1)
        ),
        annualLeaves = listOf(
            LocalDate.now().plusDays(2),
            LocalDate.now().plusDays(3),
            LocalDate.now().plusDays(4)
        ),
        forceDarkMode = false
    )
} 