package com.dayoff.core.ui.calendar

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.dayoff.core.model.AnnualLeaveRecord
import com.dayoff.core.model.calendar.CalendarDay
import com.dayoff.core.model.calendar.DayCellType
import com.dayoff.core.model.calendar.DayOfWeek
import com.dayoff.core.model.calendar.MonthType
import com.dayoff.core.ui.StampView
import com.dayoff.core.ui.model.CalendarAction
import com.dayoff.core.ui.model.ClickMode
import com.dayoff.core.ui.model.OverviewType
import com.dayoff.core.ui.model.Selection
import com.dayoff.core.ui.utils.CalendarDateUtil
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.designsystem.theme.LocalTialShapes
import java.time.LocalDate
import java.time.YearMonth


/**
 * 달력 표시
 */
@Composable
fun AnnualLeaveOverView(
    modifier: Modifier = Modifier,
    days: List<CalendarDay>,
    leaveRecords: List<AnnualLeaveRecord>,
    yearMonth: YearMonth,
    startDayOfWeek: DayOfWeek = DayOfWeek.MONDAY,
    mode: ClickMode,
    selection: Selection,
    onYearMonthChanged: (year: Int, month: Int) -> Unit,
    onSelectionChanged: OnSelectionChangedListener? = null,
    onInspect: ((LocalDate) -> Unit)? = null,
) {
    val context = LocalContext.current

    val color = LocalTialColors.current
    val shape = LocalTialShapes.current

    var selected by rememberSaveable { mutableStateOf(OverviewType.CALENDAR) }

    val daysWithSelection = remember(key1 = days, key2 = selection.selectedDates, key3 = yearMonth) {
        days.map { day ->
            if (day.day == 0) day
            else {
                val date = CalendarDateUtil.getValidDateOrNull(
                    baseMonth = yearMonth,
                    day = day.day,
                    monthType = day.monthType,
                )
                if (date in selection.selectedDates) day.copy(cellType = DayCellType.SELECTED)
                else day
            }
        }
    }

    Column(
        modifier = modifier
            .background(
                color = color.background.surface.primary,
                shape = shape.ExtraLarge,
            )
            .padding(vertical = 20.dp, horizontal = 12.dp)
    ) {
        CalendarHeader(
            year = yearMonth.year,
            month = yearMonth.monthValue,
            overviewType = selected,
            onSelectedChange = {
                selected = it
            },
            onPrevMonth = {
                if (yearMonth.monthValue > 1) {
                    val changedYearMonth = yearMonth.minusMonths(1)
                    onYearMonthChanged(changedYearMonth.year, changedYearMonth.monthValue)
                } else {
                    Toast.makeText(context, "이전 달이 없습니다.", Toast.LENGTH_SHORT).show()
                }
            },
            onNextMonth = {
                if (yearMonth.monthValue < 12) {
                    val nextYearMonth = yearMonth.plusMonths(1)
                    onYearMonthChanged(nextYearMonth.year, nextYearMonth.monthValue)
                } else {
                    Toast.makeText(context, "마지막 달 입니다.", Toast.LENGTH_SHORT).show()
                }
            },
        )
        var calendarHeight by remember { mutableStateOf(0.dp) }

        val density = LocalDensity.current

        when(selected) {
            OverviewType.CALENDAR -> CalendarView(
                modifier = Modifier.onGloballyPositioned { coords ->
                    if(selected == OverviewType.CALENDAR) {
                        calendarHeight = with(density) { coords.size.height.toDp() }
                    }
                },
                days = daysWithSelection,
                startDayOfWeek = startDayOfWeek,
                onDayClick = { day, monthType ->

                    val action = handleClickResult(
                        mode = mode,
                        yearMonth = yearMonth,
                        day = day,
                        monthType = monthType,
                        selection = selection,
                    )

                    when (action) {
                        is CalendarAction.SelectionChanged -> onSelectionChanged?.onSelectionChanged(
                            action.added, action.removed, action.next.selectedDates
                        )

                        is CalendarAction.ShowInspect -> onInspect?.invoke(action.date)
                        CalendarAction.None -> Unit
                    }
                },
            )
            OverviewType.STAMP -> Box(modifier = Modifier.fillMaxWidth().height(height = calendarHeight)) {
//                StampView(
//                    leaveRecords = leaveRecords,
//                    yearMonth = yearMonth,
//                    startDayOfWeek = startDayOfWeek,
//                )
            }
        }


    }


}

fun interface OnSelectionChangedListener {
    fun onSelectionChanged(added: Set<LocalDate>, removed: Set<LocalDate>, all: Set<LocalDate>)
}

/**
 * 날짜 클릭 후 선택 상태 반환
 */
fun handleClickResult(
    mode: ClickMode, yearMonth: YearMonth, day: Int, monthType: MonthType, selection: Selection
): CalendarAction {
    if (day == 0) return CalendarAction.None // padding cell
    val date =
        CalendarDateUtil.getValidDateOrNull(yearMonth, day, monthType) ?: return CalendarAction.None

    return when (mode) {
        ClickMode.Select -> {
            val nextSelection = CalendarDateUtil.calculateNextSelection(selection, date)
            val added = nextSelection.selectedDates - selection.selectedDates
            val removed = selection.selectedDates - nextSelection.selectedDates
            CalendarAction.SelectionChanged(nextSelection, added, removed)
        }

        ClickMode.Inspect -> CalendarAction.ShowInspect(date)
    }
}
