package com.dayoff.calendar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.YearMonth

/**
 * 캘린더 헤더
 *
 * @param currentMonth 현재 년월
 * @param onMonthChange 월 변경 콜백
 * @param onRefresh 새로고침 콜백
 * @param modifier Modifier
 */
@Composable
fun CalendarHeader(
    currentMonth: YearMonth,
    onMonthChange: (Int) -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(onClick = {
                val newMonth = if (currentMonth.monthValue == 1) 12 else currentMonth.monthValue - 1
                onMonthChange(newMonth)
            }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "이전 달"
                )
            }
            
            Text(
                text = "${currentMonth.monthValue}월",
                style = MaterialTheme.typography.titleMedium
            )
            
            IconButton(onClick = {
                val newMonth = if (currentMonth.monthValue == 12) 1 else currentMonth.monthValue + 1
                onMonthChange(newMonth)
            }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "다음 달"
                )
            }
        }

        IconButton(onClick = onRefresh) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "새로고침"
            )
        }
    }
}

/**
 * 캘린더 상단 바
 *
 * @param currentYear 현재 연도
 * @param years 표시할 연도 목록
 * @param onYearChange 연도 변경 콜백
 * @param onAddClick 추가 버튼 클릭 콜백
 * @param modifier Modifier
 */
@Composable
fun CalendarTopBar(
    currentYear: Int,
    years: List<Int> = emptyList(),
    onYearChange: (Int) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconButton(
            onClick = onAddClick,
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "연도 추가"
            )
        }

        Row(
            modifier = Modifier
                .weight(1f)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val displayYears = if (years.isEmpty()) listOf(currentYear) else years
            displayYears.forEach { year ->
                YearItem(
                    year = year,
                    isSelected = year == currentYear,
                    onClick = { onYearChange(year) }
                )
            }
        }
    }
}

@Composable
private fun YearItem(
    year: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(40.dp)
            .background(
                color = if (isSelected) {
                    MaterialTheme.colorScheme.tertiaryContainer
                } else {
                    MaterialTheme.colorScheme.surface
                },
                shape = CircleShape
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "😊",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = year.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.onTertiaryContainer
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
        }
    }
}

/** 헤더 프리뷰 */
@Preview(showBackground = true)
@Composable
private fun CalendarHeaderPreview() {
    CalendarHeader(
        currentMonth = YearMonth.now(),
        onMonthChange = {},
        onRefresh = {}
    )
}

/** 상단 바 프리뷰 */
@Preview(showBackground = true)
@Composable
private fun CalendarTopBarPreview() {
    CalendarTopBar(
        currentYear = LocalDate.now().year,
        onYearChange = {},
        onAddClick = {}
    )
}