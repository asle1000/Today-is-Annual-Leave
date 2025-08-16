package com.dayoff.feature.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dayoff.core.model.Screen
import com.dayoff.core.ui.calendar.CalendarView
import com.dayoff.core.ui.model.ClickMode
import com.dayoff.core.ui.model.Selection
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.designsystem.theme.LocalTialTypes
import com.dayoff.feature.home.components.AddAnnualEventFab
import com.dayoff.feature.home.components.AnnualYearButtonGroup
import org.koin.androidx.compose.koinViewModel

/**
 *  Created by KyunghyunPark at 2025. 7. 29.

 */
@Preview
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(), onNavigate: (Screen) -> Unit = {}
) {
    val context = LocalContext.current
    val color = LocalTialColors.current

    var selectedAnnualIdx by remember { mutableIntStateOf(0) }

    val yearMonth by viewModel.yearMonth.collectAsState()
    val days by viewModel.calendarEvents.collectAsState()

    val yearRange by viewModel.observeYearManagementInfo().collectAsState(initial = null)

    val selection by remember { mutableStateOf(Selection()) }

    LaunchedEffect(selectedAnnualIdx) {

        if (yearRange.isNullOrEmpty()) {
            return@LaunchedEffect
        }

        val item = yearRange?.get(index = selectedAnnualIdx) ?: return@LaunchedEffect

        viewModel.onYearMonthChanged(
            year = item.annualLeaveYear, month = yearMonth.monthValue
        )
    }

    LaunchedEffect(key1 = yearMonth.year) {
        viewModel.fetchCalendarEvents(year = yearMonth.year)
    }


    Scaffold(
        modifier = Modifier.background(color.background.base.primary),
        floatingActionButton = {
            AddAnnualEventFab {
                onNavigate(Screen.LeaveUsage)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color.background.base.primary)
                .padding(innerPadding)
        ) {
            AnnualYearButtonGroup(
                modifier = Modifier.padding(top = 24.dp, bottom = 20.dp, start = 12.dp),
                annualYearList = yearRange?.map { it.annualLeaveYear.toString() } ?: emptyList(),
                selectIdx = selectedAnnualIdx,
                onAdded = {
                    onNavigate(Screen.YearManager)
                },
                onSelected = { idx ->
                    selectedAnnualIdx = idx
                })

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                when (yearRange?.size) {
                    null -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(32.dp),
                                color = color.background.brand.primary,
                            )
                        }
                    }

                    0 -> {
                        Empty()
                    }

                    else -> {
                        CalendarView(
                            days = days,
                            yearMonth = yearMonth,
                            mode = ClickMode.Inspect, // or Inspect
                            selection = selection,
                            onYearMonthChanged = viewModel::onYearMonthChanged,
                            onInspect = { date ->

                                days.find {
                                    it.day == date.dayOfMonth && yearMonth.monthValue == date.monthValue && yearMonth.year == date.year
                                }?.let {
                                    Toast.makeText(context, it.name, Toast.LENGTH_SHORT).show()
                                }

                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Empty() {
    val color = LocalTialColors.current
    val style = LocalTialTypes.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.weight(1f))

        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.img_logo),
            contentDescription = null,
        )

        Spacer(Modifier.height(9.dp))

        Text(
            text = "연차 관리 어렵지 않아요",
            style = style.titleLarge.copy(color = color.text.surface.secondary),
        )

        Spacer(modifier = Modifier.height(height = 4.dp))

        Text(
            text = "상단 + 버튼을 눌러 지금 바로 시작해 보세요.",
            style = style.bodyLarge.copy(
                color = color.text.surface.tertiary
            ),
        )

        Spacer(Modifier.weight(3f))
    }
}