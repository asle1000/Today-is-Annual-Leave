package com.dayoff.manager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.dayoff.core.model.calendar.CalendarDay
import com.dayoff.core.navigation.Screen
import com.dayoff.core.navigation.appNavGraph
import timber.log.Timber
import java.time.YearMonth

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Screen.Splash.route
            ) {
                appNavGraph(navController)
            }
        }


//        setContent {
//            val yearRange = (2024..2026).toList()
//
//            val yearMonth by viewModel.yearMonth.collectAsState()
//            val list by viewModel.calendarEvents.collectAsState()
//
//            LaunchedEffect(yearMonth.year) {
//                Timber.d("LaunchedEffect: ${yearMonth.year}")
//                repository.fetchCalendarEvents(yearMonth.year)
//            }
//
//            Surface(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.White)
//                    .padding(horizontal = 12.dp),
//                color = Color.White
//            ) {
//                CalendarScreen(
//                    yearRange = yearRange,
//                    yearMonth = yearMonth,
//                    list = list,
//                    onChanged = viewModel::onYearMonthChanged
//                )
//            }
//        }
    }
}

@Composable
fun CalendarScreen(
    yearRange: List<Int>,
    yearMonth: YearMonth,
    list: List<CalendarDay>,
    onChanged: (Int, Int) -> Unit,
) {
    Timber.d("CalendarScreen: \n${list.joinToString("\n")}")
    Column {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            yearRange.forEach { year ->
                Button(
                    onClick = {
                        onChanged(year, yearMonth.monthValue)
                    },
                    modifier = Modifier.padding(horizontal = 4.dp),
                    enabled = yearMonth.year != year
                ) {
                    Text(text = year.toString())
                }
            }
        }
        com.dayoff.core.ui.calendar.MonthCalendar(
            days = list,
            yearMonth = yearMonth,
            onPrevMonth = {
                if (yearMonth.monthValue > 1) {
                    val yearMonth = yearMonth.minusMonths(1)
                    onChanged(yearMonth.year, yearMonth.monthValue)
                }
            },
            onNextMonth = {
                if (yearMonth.monthValue < 12) {
                    val yearMonth = yearMonth.plusMonths(1)
                    onChanged(yearMonth.year, yearMonth.monthValue)
                }
            },
            onRefresh = {

            }
        )
    }
} 