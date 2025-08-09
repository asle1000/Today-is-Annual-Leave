package com.dayoff.feature.leave_usage

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.dayoff.feature.leave_usage.component.AnnualLeaveConsumptionSelector
import com.dayoff.feature.leave_usage.component.AnnualLeaveTypeSelector
import com.dayoff.feature.leave_usage.component.LeaveSelector
import com.dayoff.feature.leave_usage.component.TimePickerSelector
import org.koin.androidx.compose.koinViewModel

/**
 *  Created by KyunghyunPark at 2025. 8. 9.

 */
@Composable
fun LeaveRegistrationScreen(
    viewModel: LeaveRegistrationViewModel = koinViewModel(),
    onBack: () -> Unit = {}
) {
    val color = LocalTialColors.current
    val scrollState = rememberScrollState()

    val yearMonth by viewModel.yearMonth.collectAsStateWithLifecycle()
    val list by viewModel.calendarEvents.collectAsStateWithLifecycle()
    val yearRange by viewModel.observeYearManagementInfo()
        .collectAsStateWithLifecycle(initialValue = null)

    val selectedAnnualIdx by rememberSaveable { mutableIntStateOf(0) }

    val showTopFade by remember { derivedStateOf { scrollState.value > 0 } }
    val showBottomFade by remember { derivedStateOf { scrollState.value < scrollState.maxValue } }

    LaunchedEffect(
        selectedAnnualIdx,
        yearRange?.getOrNull(selectedAnnualIdx)?.annualLeaveYear,
        yearMonth.monthValue
    ) {
        val item = yearRange?.getOrNull(selectedAnnualIdx) ?: return@LaunchedEffect
        viewModel.onYearMonthChanged(year = item.annualLeaveYear, month = yearMonth.monthValue)
    }

    Scaffold(
        modifier = Modifier.background(color = color.background.base.white),
        topBar = {
            TopAppBar(
                title = "사용 연차 추가",
                prefixContent = { BackIconButton(onClick = onBack) },
            )
        }
    ) { innerPadding ->

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
                        modifier = Modifier.fillMaxWidth(),
                        onSelected = {

                        }
                    )

                    Spacer(Modifier.height(40.dp))

                    AnnualLeaveTypeSelector(
                        modifier = Modifier.fillMaxWidth(),
                        onSelected = { }
                    )

                    Spacer(Modifier.height(40.dp))

                    TimePickerSelector(
                        modifier = Modifier.fillMaxWidth(),
                        hour = "00",
                        minute = "00",
                        onHourSelected = { },
                        onMinuteSelected = { }
                    )

                    Spacer(Modifier.height(40.dp))

                    LeaveSelector(
                        yearMonth = yearMonth,
                        list = list,
                        onChanged = viewModel::onYearMonthChanged
                    )

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
                                    0f to Color.Black.copy(alpha = 0.10f),
                                    1f to Color.Transparent
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
                                    0f to Color.Transparent,
                                    1f to Color.Black.copy(alpha = 0.10f)
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

            }
        }
    }
}




