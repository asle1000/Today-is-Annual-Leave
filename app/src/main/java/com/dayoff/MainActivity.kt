package com.dayoff

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dayoff.calendar.ui.Calendar
import com.dayoff.manager.ui.theme.TodayIsAnnualLeaveTheme
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodayIsAnnualLeaveTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        Calendar(
                            modifier = Modifier.fillMaxWidth(),
                            holidays = listOf(
                                LocalDate.now(),
                                LocalDate.now().plusDays(7)
                            ),
                            alternativeHolidays = listOf(
                                LocalDate.now().plusDays(1)
                            ),
                            annualLeaves = listOf(
                                LocalDate.now().plusDays(2),
                                LocalDate.now().plusDays(3),
                                LocalDate.now().plusDays(4)
                            ),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TodayIsAnnualLeaveTheme {
        Greeting("Android")
    }
}