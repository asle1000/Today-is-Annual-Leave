package com.dayoff.core.ui.calendar

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.dayoff.core.model.AnnualLeaveRecord
import com.dayoff.core.ui.R
import com.dayoff.core.ui.model.StampConfig
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.designsystem.theme.LocalTialShapes
import kotlin.math.abs
import kotlin.math.ceil

/**
 * Stamp view
 *
 * @param modifier
 * @param total 총 연차 수
 * @param records 등록된 연차 수
 * @param columns 세로 아이템 수
 * @param rows 가로 스탬프 수
 */
@Preview
@Composable
fun StampView(
    modifier: Modifier = Modifier,
    total: Int = 15,
    records: List<AnnualLeaveRecord> = emptyList(),
    columns: Int = 2,
    rows: Int = 5
) {
    val color = LocalTialColors.current
    val shape = LocalTialShapes.current

    val stampConfig = remember(records, total) {
        calculateStampConfig(records, total, columns, rows)
    }

    var pageHeight by remember { mutableIntStateOf(0) }

    val pagerState = rememberPagerState(pageCount = { stampConfig.pageCount })

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = color.background.brand.tertiary)
            .padding(vertical = 14.dp, horizontal = 10.dp),
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .clip(shape.Large)
                .background(color.background.base.white),
            verticalAlignment = Alignment.Top
        ) { page ->
            StampPage(
                page = page,
                total = total,
                stampConfig = stampConfig,
                pageHeight = pageHeight,
                onHeightMeasured = { height ->
                    if (page == 0 && pageHeight == 0) {
                        pageHeight = height
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        PagingIndicator(
            pagerState = pagerState,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            activeColor = color.background.brand.primary,
            inactiveColor = color.background.brand.secondary,
        )
    }
}

/**
 * 개별 페이지의 스탬프 그리드
 */
@Composable
private fun StampPage(
    page: Int,
    total: Int,
    stampConfig: StampConfig,
    pageHeight: Int,
    onHeightMeasured: (Int) -> Unit
) {
    val density = LocalDensity.current

    val start = page * stampConfig.countPerPage
    val end = minOf(total, start + stampConfig.countPerPage)

    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 12.dp, bottom = 20.dp)
            .run {
                if (pageHeight > 0) {
                    height(with(density) { pageHeight.toDp() })
                } else this
            }
            .onGloballyPositioned { coordinates ->
                onHeightMeasured(coordinates.size.height)
            },
        maxItemsInEachRow = stampConfig.rows,
        maxLines = stampConfig.columns,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        (start until end).forEach { globalIdx ->
            val number = total - globalIdx
            val tint = getStampColor(
                globalIdx = globalIdx,
                hoursCnt = stampConfig.usedHoursStampCnt,
                hasMinutesStamp = stampConfig.hasMinutesStamp,
            )

            StampItem(
                text = number.toString(),
                iconResource = R.drawable.ic_leave_clover,
                tint = tint,
            )
        }
    }
}

/**
 * 개별 스탬프 아이템
 */
@Composable
private fun StampItem(
    modifier: Modifier = Modifier,
    @DrawableRes iconResource: Int,
    tint: Color,
    text: String,
) {
    val color = LocalTialColors.current

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconResource),
            tint = tint,
            contentDescription = null
        )

        Text(
            text = text,
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 36.sp,
                fontFamily = FontFamily(
                    Font(resId = com.dayoff.designsystem.R.font.y_clover_bold)
                ),
                color = color.background.base.white
            )
        )
    }
}

/**
 * 페이지 인디케이터
 */
@Composable
private fun PagingIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    activeColor: Color = Color(0xFF6FD4A3),
    inactiveColor: Color = Color(0xFFD4F4E5),
    maxWidth: Dp = 32.dp,
    minWidth: Dp = 10.dp,
    height: Dp = 10.dp,
    spacing: Dp = 6.dp
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pagerState.pageCount) { index ->
            val offset = pagerState.currentPage + pagerState.currentPageOffsetFraction
            val distance = abs(offset - index)
            val progress = (1f - distance).coerceIn(0f, 1f)

            val width by animateDpAsState(
                targetValue = lerp(minWidth, maxWidth, progress),
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                ),
                label = "width"
            )

            val color by animateColorAsState(
                targetValue = lerp(inactiveColor, activeColor, progress),
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                ),
                label = "color"
            )

            Box(
                modifier = Modifier
                    .size(width = width, height = height)
                    .background(
                        color = color,
                        shape = RoundedCornerShape(height / 2)
                    )
            )
        }
    }
}

/**
 * 연차 사용 내역을 기반으로 스탬프 설정 계산
 */
private fun calculateStampConfig(
    itemList: List<AnnualLeaveRecord>,
    total: Int,
    columns: Int,
    rows: Int
): StampConfig {
    val totalMinutes = itemList.sumOf { it.minutes ?: 0 }
    val usedHours = totalMinutes / 60
    val usedMinutes = totalMinutes % 60

    val usedHoursStampCnt = usedHours.coerceIn(0, total)
    val usedMinutesStampCnt = if (usedMinutes > 0) 1 else 0
    val hasMinutesStamp = usedMinutesStampCnt > 0 && usedHoursStampCnt < total

    val countPerPage = (columns * rows).coerceAtLeast(1)
    val pageCount = ceil(total.toDouble() / countPerPage).toInt().coerceAtLeast(1)

    return StampConfig(
        usedHoursStampCnt = usedHoursStampCnt,
        hasMinutesStamp = hasMinutesStamp,
        countPerPage = countPerPage,
        pageCount = pageCount,
        columns = columns,
        rows = rows
    )
}

/**
 * 스탬프의 색상 결정
 * @param globalIdx 전역 인덱스 (0부터 시작)
 * @param hoursCnt 사용한 시간 스탬프 개수
 * @param hasMinutesStamp 분 스탬프 존재 여부
 */
@Composable
private fun getStampColor(
    globalIdx: Int,
    hoursCnt: Int,
    hasMinutesStamp: Boolean,
): Color {
    val color = LocalTialColors.current
    return when {
        globalIdx < hoursCnt -> color.background.brand.tertiary
        globalIdx == hoursCnt && hasMinutesStamp -> color.background.brand.secondary
        else -> color.background.brand.primary
    }
}