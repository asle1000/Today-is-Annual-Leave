package com.dayoff.feature.leave_usage.component

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
import androidx.compose.ui.unit.dp
import com.dayoff.core.model.calendar.CalendarDay
import com.dayoff.core.ui.TialIconRadioButtonHorizontalGroup
import com.dayoff.core.ui.TialTextRadioButtonVerticalGroup
import com.dayoff.core.ui.basic.BasicExposedDropdown
import com.dayoff.core.ui.calendar.CalendarView
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.designsystem.theme.LocalTialTypes
import com.dayoff.feature.leave_usage.model.AnnualLeaveType
import com.dayoff.feature.leave_usage.model.ConsumptionType
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

    SubTitle("연차 소진 여부를 선택해 주세요")

    Spacer(modifier = Modifier.height(12.dp))

    TialTextRadioButtonVerticalGroup(
        modifier = modifier,
        options = options,
        onSelectionChanged = { idx ->
            onSelected(ConsumptionType.entries[idx])
        },
    )
}

@Composable
fun AnnualLeaveTypeSelector(
    modifier: Modifier = Modifier,
    onSelected: (AnnualLeaveType) -> Unit,
) {
    val options = AnnualLeaveType.toList()

    SubTitle("사용할 연차 종류를 선택해 주세요")

    Spacer(modifier = Modifier.height(12.dp))

    TialIconRadioButtonHorizontalGroup(
        modifier = modifier,
        options = options,
        onSelectionChanged = { idx ->
            onSelected(AnnualLeaveType.entries[idx])
        },
    )
}

@Composable
fun LeaveSelector(
    yearMonth: YearMonth,
    list: List<CalendarDay>,
    onChanged: (Int, Int) -> Unit,
) {
    SubTitle("사용할 날짜가 언제인가요?")

    Spacer(Modifier.height(8.dp))

    CalendarView(
        yearMonth = yearMonth,
        list = list,
        onChanged = onChanged
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
    val style = LocalTialTypes.current
    val color = LocalTialColors.current

    val hourItems = (1..23).map { it.toString().padStart(2, '0') }
    val minuteItems = (0..59).map { it.toString().padStart(2, '0') }

    SubTitle("사용할 시간을 선택해 주세요")

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

