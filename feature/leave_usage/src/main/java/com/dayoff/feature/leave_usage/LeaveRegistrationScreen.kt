package com.dayoff.feature.leave_usage

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dayoff.core.model.AnnualLeaveRecord
import com.dayoff.core.ui.basic.BackIconButton
import com.dayoff.core.ui.basic.TopAppBar
import com.dayoff.core.ui.button.TialButton
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.core.ui.model.AnnualLeaveConsumptionSelector
import com.dayoff.core.model.calendar.AnnualLeaveType
import com.dayoff.core.ui.model.LeaveTypeSelector
import com.dayoff.core.ui.model.ConsumptionType
import com.dayoff.core.ui.model.LeaveSelector
import com.dayoff.core.ui.model.Selection
import com.dayoff.core.ui.model.TimePickerSelector
import com.dayoff.designsystem.theme.LocalTialShapes
import com.dayoff.designsystem.theme.LocalTialTypes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber
import java.time.LocalDate

/**
 *  Created by KyunghyunPark at 2025. 8. 9.

 */
@Composable
fun LeaveRegistrationScreen(
    viewModel: LeaveRegistrationViewModel = koinViewModel(),
    onBack: () -> Unit = {},
    onDone: () -> Unit = {},
) {
    val color = LocalTialColors.current

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

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

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(key1 = snackBarHostState.currentSnackbarData) {
        // TODO 연속 클릭 시, 스낵바가 3000ms보다 빠르게 꺼지는 현상
        coroutineScope.launch {
            delay(3000)
            snackBarHostState.currentSnackbarData?.dismiss()
        }
    }

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
        modifier = Modifier.background(color = color.background.base.white),
        topBar = {
            TopAppBar(
                modifier = Modifier.background(color = color.background.base.white),
                title = "사용 연차 추가",
                prefixContent = { BackIconButton(onClick = onBack) },
            )
        },
        snackbarHost = { },
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color.background.base.white)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
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

                        LeaveTypeSelector(
                            modifier = Modifier.fillMaxWidth(),
                            onSelected = {
                                selectedAnnualLeaveType = it
                            },
                        )

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
                    fun LocalDate.toYmd(): Int = year * 10000 + monthValue * 100 + dayOfMonth

                    suspend fun showSnack(message: String) {
                        snackBarHostState.currentSnackbarData?.dismiss()
                        snackBarHostState.showSnackbar(
                            message = message,
                            withDismissAction = true,
                            duration = SnackbarDuration.Short
                        )
                    }

                    val dates = selection.selectedDates
                    val count = dates.size

                    when (selectedAnnualLeaveType) {
                        AnnualLeaveType.FULL, AnnualLeaveType.HALF_AM, AnnualLeaveType.HALF_FM -> {
                            if (count == 0) {
                                coroutineScope.launch { showSnack("사용할 날짜를 선택해 주세요.") }
                                return@TialButton
                            }

                            val startYmd = dates.minOf(LocalDate::toYmd)
                            val endYmd = dates.maxOf(LocalDate::toYmd)

                            val totalMinutes =
                                if (selectedAnnualLeaveType == AnnualLeaveType.HALF_AM ||
                                    selectedAnnualLeaveType == AnnualLeaveType.HALF_FM
                                ) 4 * 60
                                else count * 8 * 60

                            val record = AnnualLeaveRecord(
                                id = 0L,
                                startYmd = startYmd,
                                endYmd = endYmd,
                                minutes = totalMinutes,
                                isConsumed = false,
                                type = selectedAnnualLeaveType,
                                memo = null,
                                modifiedAt = System.currentTimeMillis()
                            )

                            coroutineScope.launch {
                                viewModel.registerAnnualLeave(record).fold(
                                    onSuccess = {
                                        Timber.d("onSuccess: $it")
                                        onDone()
                                    },
                                    onFailure = {
                                        showSnack("해당 날 ")
                                        Timber.d("onFailure: $it")
                                    }
                                )
                            }
                        }

                        AnnualLeaveType.HOURLY -> {
                            if (count == 0) {
                                coroutineScope.launch { showSnack("사용할 날짜를 선택해 주세요.") }
                                return@TialButton
                            }
                            if (count > 1) {
                                coroutineScope.launch { showSnack("시간차는 하루 선택만 가능 합니다.") }
                                return@TialButton
                            }

                            val usedMinutes =
                                (hour.toIntOrNull() ?: 0) * 60 + (minutes.toIntOrNull() ?: 0)


                            if (usedMinutes == 0) {
                                coroutineScope.launch { showSnack("사용할 시간을 선택해 주세요.") }
                                return@TialButton
                            }

                            val ymd = dates.first().toYmd()

                            val record = AnnualLeaveRecord(
                                id = 0L,
                                startYmd = ymd,
                                endYmd = ymd,
                                minutes = usedMinutes,
                                isConsumed = false,
                                type = selectedAnnualLeaveType,
                                memo = null,
                                modifiedAt = System.currentTimeMillis()
                            )

                            coroutineScope.launch {
                                viewModel.registerAnnualLeave(record)
                                onDone()
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

            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier
                    .align(Alignment.Center)
                    .imePadding()
                    .systemBarsPadding(),
                snackbar = {
                    CompactSnackBar(
                        data = it,
                        containerColor = color.background.utilities.toast,
                        contentColor = color.text.surface.primaryOn,
                    )
                },
            )
        }
    }
}

@Composable
fun CompactSnackBar(
    data: SnackbarData,
    modifier: Modifier = Modifier,
    containerColor: Color,
    contentColor: Color,
) {
    val shape = LocalTialShapes.current
    val typo = LocalTialTypes.current

    Surface(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight(),
        shape = shape.Small,
        color = containerColor,
        tonalElevation = 6.dp,
        shadowElevation = 6.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(all = 12.dp),
                text = data.visuals.message,
                style = typo.bodyMedium.copy(color = contentColor),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


