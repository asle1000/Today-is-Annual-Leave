package com.dayoff

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dayoff.designsystem.theme.TodayIsAnnualLeaveTheme

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
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // 색상 사용 예시
                            ColorUsageExample()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ColorUsageExample() {
    val colorScheme = MaterialTheme.colorScheme
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorScheme.surface)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 배경색 예시
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(colorScheme.primary)
        )
        
        // 텍스트 색상 예시
        Text(
            text = "브랜드 텍스트",
            color = colorScheme.onPrimary,
            modifier = Modifier.padding(8.dp)
        )
        
        Text(
            text = "서피스 텍스트",
            color = colorScheme.onSurface,
            modifier = Modifier.padding(8.dp)
        )
        
        // 버튼 예시
        Button(
            onClick = { },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("브랜드 버튼")
        }
        
        // 보더 색상 예시
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(colorScheme.background)
                .padding(2.dp)
                .background(colorScheme.outline)
        )
        
        Text(
            text = "보더 색상",
            color = colorScheme.onSurface,
            modifier = Modifier.padding(8.dp)
        )
        
        // Typography 예시
        Text(
            text = "Display Large",
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(8.dp)
        )
        
        Text(
            text = "Headline Medium",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )
        
        Text(
            text = "Body Large",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(8.dp)
        )
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

@Preview(showBackground = true)
@Composable
fun ColorUsageExamplePreview() {
    TodayIsAnnualLeaveTheme {
        ColorUsageExample()
    }
}