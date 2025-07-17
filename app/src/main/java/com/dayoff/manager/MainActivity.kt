package com.dayoff.manager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.dayoff.data.datasource.CalendarEventRemoteDataSource
import com.dayoff.data.datasource.CalendarEventRemoteDataSourceImpl
import com.dayoff.designsystem.components.calendar.MonthCalendar
import com.dayoff.designsystem.model.DayCellIndicatorType
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.YearMonth
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val calendarEventRemoteDataSource: CalendarEventRemoteDataSource by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val res = calendarEventRemoteDataSource.fetchCalendarEvent()

        }


        setContent {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(horizontal = 12.dp),
                color = Color.White
            ) {
                CalendarScreen()
            }
        }
    }
}

@Composable
fun CalendarScreen() {
    val yearRange = (2024..2026).toList()
    val today = LocalDate.now()
    var yearMonth by remember { mutableStateOf(YearMonth.of(today.year, today.monthValue)) }

    Column {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            yearRange.forEach { year ->
                Button(
                    onClick = {
                        yearMonth = YearMonth.of(year, yearMonth.monthValue)
                    },
                    modifier = Modifier.padding(horizontal = 4.dp),
                    enabled = yearMonth.year != year
                ) {
                    Text(text = year.toString())
                }
            }
        }
        MonthCalendar(
            yearMonth = yearMonth,
            today = today,
            indicatorResolver = { date ->
                DayCellIndicatorType.NONE
            },
            onPrevMonth = {
                if (yearMonth.monthValue > 1) yearMonth = yearMonth.minusMonths(1)
            },
            onNextMonth = {
                if (yearMonth.monthValue < 12) yearMonth = yearMonth.plusMonths(1)
            },
            onRefresh = {

            }
        )
    }
} 