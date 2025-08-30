package com.dayoff.feature.leave_usage

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dayoff.core.ui.basic.BackIconButton
import com.dayoff.core.ui.basic.TopAppBar
import com.dayoff.core.ui.button.TialButton
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.core.ui.model.AnnualLeaveConsumptionSelector
import com.dayoff.core.model.calendar.AnnualLeaveType
import com.dayoff.core.ui.model.AnnualLeaveTypeSelector
import com.dayoff.core.ui.model.ConsumptionType
import com.dayoff.core.ui.model.LeaveSelector
import com.dayoff.core.ui.model.Selection
import com.dayoff.core.ui.model.TimePickerSelector
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

/**
 *  Created by KyunghyunPark at 2025. 8. 9.

 */
@Composable
fun LeaveRegistrationScreen(
    viewModel: LeaveRegistrationViewModel = koinViewModel(), onBack: () -> Unit = {}
) {
    val color = LocalTialColors.current
    val scrollState = rememberScrollState()

    val yearMonth by viewModel.yearMonth.collectAsStateWithLifecycle()
    val calendarDays by viewModel.calendarEvents.collectAsStateWithLifecycle()

    var hour by remember {
        mutableStateOf("00")
    }

    var minutes by remember {
        mutableStateOf("00")
    }

    val yearRange by viewModel.observeYearManagementInfo()
        .collectAsStateWithLifecycle(initialValue = null)

    val selectedAnnualIdx by rememberSaveable { mutableIntStateOf(0) }

    var selectedConsumptionType by rememberSaveable {
        mutableStateOf(
            value = ConsumptionType.CONSUME
        )
    }

    var selectedAnnualLeaveType by rememberSaveable {
        mutableStateOf(
            value = AnnualLeaveType.FULL
        )
    }

    var selection by remember { mutableStateOf(Selection()) }

    val showTopFade by remember { derivedStateOf { scrollState.value > 0 } }
    val showBottomFade by remember { derivedStateOf { scrollState.value < scrollState.maxValue } }

    LaunchedEffect(
        key1 = selectedAnnualIdx,
        key2 = yearRange?.getOrNull(selectedAnnualIdx)?.annualLeaveYear,
        key3 = yearMonth.monthValue
    ) {
        val item = yearRange?.getOrNull(selectedAnnualIdx) ?: return@LaunchedEffect
        viewModel.onYearMonthChanged(year = item.annualLeaveYear, month = yearMonth.monthValue)
    }

    LaunchedEffect(key1 = selectedAnnualLeaveType) {
        if (selectedAnnualLeaveType == AnnualLeaveType.HOURLY) {
            selection = Selection()
        } else {
            hour = "00"
            minutes = "00"
        }
    }

    val isHourly = (selectedAnnualLeaveType == AnnualLeaveType.HOURLY)

    LaunchedEffect(isHourly) {
        if (isHourly && selection.selectedDates.size > 1) {
            val keepOne = selection.selectedDates.minOrNull() // 규칙: 가장 이른 날짜
            selection = selection.copy(selectedDates = keepOne?.let { setOf(it) } ?: emptySet())
        }
    }

    Scaffold(
        modifier = Modifier.background(color = color.background.base.white), topBar = {
            TopAppBar(
                modifier = Modifier.background(color = color.background.base.white),
                title = "사용 연차 추가",
                prefixContent = { BackIconButton(onClick = onBack) },
            )
        }) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color.background.base.white)
        ) {
            Box(Modifier.weight(1f)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .overscroll(null)
                        .padding(horizontal = 12.dp, vertical = 20.dp),
                ) {
                    AnnualLeaveConsumptionSelector(
                        modifier = Modifier.fillMaxWidth(), onSelected = {
                            selectedConsumptionType = it
                        })

                    Spacer(Modifier.height(40.dp))

                    AnnualLeaveTypeSelector(
                        modifier = Modifier.fillMaxWidth(), onSelected = {
                            selectedAnnualLeaveType = it
                        })

                    Spacer(Modifier.height(40.dp))

                    AnimatedVisibility(
                        visible = (selectedAnnualLeaveType == AnnualLeaveType.HOURLY),
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            TimePickerSelector(
                                modifier = Modifier.fillMaxWidth(),
                                hour = hour,
                                minute = minutes,
                                onHourSelected = {
                                    hour = it
                                },
                                onMinuteSelected = {
                                    minutes = it
                                })

                            Spacer(Modifier.height(40.dp))
                        }
                    }

                    LeaveSelector(
                        yearMonth = yearMonth,
                        calendarDays = calendarDays,
                        onYearMonthChanged = viewModel::onYearMonthChanged,
                        selection = selection,
                        onSelectionChanged = { all ->
                            selection = selection.copy(
                                selectedDates = if (isHourly) {
                                    val added = all - selection.selectedDates
                                    val removed = selection.selectedDates - all

                                    when {
                                        added.isNotEmpty() -> setOf(added.last())
                                        removed.isNotEmpty() -> emptySet()
                                        else -> selection.selectedDates
                                    }
                                } else {
                                    all
                                }
                            )
                        })

                    Spacer(Modifier.height(52.dp))
                }

                if (showTopFade) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                            .align(Alignment.TopCenter)
                            .background(
                                Brush.verticalGradient(
                                    0f to Color.Black.copy(alpha = 0.10f), 1f to Color.Transparent
                                )
                            )
                    )
                }
                if (showBottomFade) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                            .align(Alignment.BottomCenter)
                            .background(
                                Brush.verticalGradient(
                                    0f to Color.Transparent, 1f to Color.Black.copy(alpha = 0.10f)
                                )
                            )
                    )
                }
            }

            TialButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 20.dp),
                text = "확인",
                enabled = true
            ) {

                when (selectedAnnualLeaveType) {
                    AnnualLeaveType.FULL, AnnualLeaveType.HALF -> {
                        when(selection.selectedDates.size) {
                            0 -> {
                                // 하루 이상의 날짜 선택이 필요
                                return@TialButton
                            }

                            else -> {
                                // 등록 절차 진행
                            }
                        }
                    }

                    AnnualLeaveType.HOURLY -> {

                        when(selection.selectedDates.size) {
                            0 -> {
                                // 하루의 날짜 선택이 필요
                                return@TialButton
                            }
                            1 -> {
                                if (hour.toInt() == 0 && minutes.toInt() == 0) {
                                    // 최소 30분 이상 선택
                                    return@TialButton
                                }

                                // 등록 절차 진행
                            }
                            else -> {
                                // 오류 케이스 -> 시간차는 하루 - 특정 시간 사용
                            }
                        }
                    }
                }

                Timber.d(
                    """
                    [TEST] 결과
                    
                    소진 여부: $selectedConsumptionType
                    연차 종류: $selectedAnnualLeaveType
                    
                    ${
                        if (selectedAnnualLeaveType == AnnualLeaveType.HOURLY) {
                            "시간 사용: $hour : $minutes"
                        } else {
                            "날짜 사용: ${selection.selectedDates.map { it.toString() }}"
                        }
                    }
                """.trimIndent()
                )

            }
        }
    }
}




