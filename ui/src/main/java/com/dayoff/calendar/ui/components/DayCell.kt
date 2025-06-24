package com.dayoff.calendar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dayoff.calendar.ui.model.DayInfo
import java.time.LocalDate
import java.time.YearMonth

/**
 * 날짜 셀
 *
 * @param dayInfo 날짜 정보
 * @param yearMonth 현재 표시 중인 년월
 * @param isSelected 선택 여부
 * @param onClick 클릭 콜백
 * @param modifier Modifier
 */
@Composable
fun DayCell(
    dayInfo: DayInfo,
    yearMonth: YearMonth,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isCurrentMonth = dayInfo.date.month == yearMonth.month && dayInfo.date.year == yearMonth.year
    
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .background(
                when {
                    dayInfo.isRecommendedDay -> MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f)
                    else -> Color.Transparent
                },
                RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (dayInfo.isRecommendedDay) {
            RecommendedLabel()
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DayText(
                dayOfMonth = dayInfo.date.dayOfMonth,
                isCurrentMonth = isCurrentMonth
            )
            
            StatusIndicators(dayInfo = dayInfo)
        }
    }
}

@Composable
private fun RecommendedLabel() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .background(
                    color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(horizontal = 4.dp, vertical = 2.dp)
        ) {
            Text(
                text = "추천 픽",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Composable
private fun DayText(
    dayOfMonth: Int,
    isCurrentMonth: Boolean
) {
    Text(
        text = dayOfMonth.toString(),
        style = MaterialTheme.typography.bodyMedium,
        color = if (isCurrentMonth) Color(0xFF334155) else Color(0xFFCBD5E1)
    )
}

@Composable
private fun StatusIndicators(
    dayInfo: DayInfo,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier.padding(top = 2.dp)
    ) {
        if (dayInfo.isHoliday) {
            StatusDot(color = MaterialTheme.colorScheme.error)
        }
        if (dayInfo.isAlternativeHoliday) {
            StatusDot(color = MaterialTheme.colorScheme.tertiary)
        }
        if (dayInfo.isAnnualLeave) {
            StatusDot(color = Color(0xFF4CAF50).copy(alpha = 0.8f))
        }
        if (dayInfo.isRecommendedDay) {
            StatusDot(color = Color(0xFF4CAF50))
        }
    }
}

@Composable
private fun StatusDot(
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(4.dp)
            .clip(CircleShape)
            .background(color)
    )
}

/**
 * 날짜 셀 프리뷰
 */
@Preview
@Composable
private fun DayCellPreview() {
    val date = LocalDate.now()
    DayCell(
        dayInfo = DayInfo(
            date = date,
            isHoliday = true,
            isAlternativeHoliday = true,
            isAnnualLeave = true,
            isRecommendedDay = true
        ),
        yearMonth = YearMonth.now(),
        isSelected = false,
        onClick = {}
    )
}
