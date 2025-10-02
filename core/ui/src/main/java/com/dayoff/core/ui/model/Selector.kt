package com.dayoff.core.ui.model

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dayoff.core.model.calendar.AnnualLeaveType
import com.dayoff.core.model.calendar.CalendarDay
import com.dayoff.core.ui.R
import com.dayoff.core.ui.TialIconRadioButtonGridGroup
import com.dayoff.core.ui.TialTextRadioButtonVerticalGroup
import com.dayoff.core.ui.basic.BasicExposedDropdown
import com.dayoff.core.ui.calendar.AnnualLeaveOverView
import com.dayoff.core.ui.calendar.SubTitle
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.designsystem.theme.LocalTialTypes
import java.time.LocalDate
import java.time.YearMonth

/**
 *  Created by KyunghyunPark at 2025. 8. 9.

 */
@Composable
fun AnnualLeaveConsumptionSelector(
    modifier: Modifier = Modifier,
    onSelected: (ConsumptionType) -> Unit,
) {
    val options = ConsumptionType.toList()

    SubTitle(text = "연차 소진 여부를 선택해 주세요")

    Spacer(modifier = Modifier.height(height = 12.dp))

    TialTextRadioButtonVerticalGroup(
        modifier = modifier,
        options = options,
        onSelectionChanged = { idx ->
            onSelected(ConsumptionType.entries[idx])
        },
    )
}

@Composable
fun LeaveTypeSelector(
    modifier: Modifier = Modifier,
    onSelected: (AnnualLeaveType) -> Unit,
) {
    val options = AnnualLeaveType.entries.map { type ->
        return@map when (type) {
            AnnualLeaveType.FULL -> R.drawable.img_annual_leave to type.label
            AnnualLeaveType.HALF_AM -> R.drawable.img_half_annual_leave to type.label
            AnnualLeaveType.HALF_FM -> R.drawable.img_half_annual_leave to type.label
            AnnualLeaveType.HOURLY -> R.drawable.img_particle_day_leave to type.label
        }
    }

    SubTitle(text = "사용할 연차 종류를 선택해 주세요")

    Spacer(modifier = Modifier.height(height = 12.dp))

    TialIconRadioButtonGridGroup(
        modifier = modifier,
        options = options,
        columns = 2,
        onSelectionChanged = { idx ->
            onSelected(AnnualLeaveType.entries[idx])
        }
    )
}

@Composable
fun LeaveSelector(
    yearMonth: YearMonth,
    calendarDays: List<CalendarDay>,
    selection: Selection,
    onYearMonthChanged: (Int, Int) -> Unit,
    onSelectionChanged: (Set<LocalDate>) -> Unit
) {
    SubTitle("사용할 날짜가 언제인가요?")

    Spacer(Modifier.height(8.dp))

    AnnualLeaveOverView(
        days = calendarDays,
        yearMonth = yearMonth,
        mode = ClickMode.Select,
        selection = selection,
        onYearMonthChanged = onYearMonthChanged,
        onSelectionChanged = { _, _, all ->
            onSelectionChanged(all)
        },
    )
}

@Composable
fun TimePickerSelector(
    modifier: Modifier = Modifier,
    hour: String,
    minute: String,
    onHourSelected: (String) -> Unit,
    onMinuteSelected: (String) -> Unit,
) {
    val color = LocalTialColors.current
    val style = LocalTialTypes.current

    val hourItems = (1..7).map { it.toString().padStart(2, '0') }
    val minuteItems = listOf(0, 30).map { it.toString().padStart(2, '0') }

    SubTitle("사용할 시간을 선택해 주세요")

    Spacer(modifier = Modifier.height(height = 12.dp))

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(modifier = Modifier.weight(1f)) {
            BasicExposedDropdown(
                modifier = Modifier.fillMaxWidth(),
                hint = "00",
                items = hourItems,
                selectedOption = hour,
                onItemSelected = onHourSelected
            )
        }

        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = ":",
            style = style.titleLarge.copy(
                color = color.text.surface.secondary
            )
        )

        Box(modifier = Modifier.weight(1f)) {
            BasicExposedDropdown(
                modifier = Modifier.fillMaxWidth(),
                hint = "00",
                items = minuteItems,
                selectedOption = minute,
                onItemSelected = onMinuteSelected
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAnnualLeaveConsumptionSelector() {
    LeaveTypeSelector(
        modifier = Modifier, onSelected = { _ ->

        }
    )
}

