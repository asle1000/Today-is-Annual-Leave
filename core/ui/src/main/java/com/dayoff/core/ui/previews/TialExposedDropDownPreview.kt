package com.dayoff.core.ui.previews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dayoff.core.ui.basic.BasicExposedDropdown

/**
 *  Created by KyunghyunPark at 2025. 7. 2.

 */

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun Preview_BasicExposedDropdown_AllCases() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        color = Color.White
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // 1. 기본
            var selected1 by remember { mutableStateOf<String?>(null) }
            BasicExposedDropdown(
                hint = "기본 선택",
                items = listOf("서울", "부산", "광주"),
                selectedOption = selected1,
                onItemSelected = { selected1 = it }
            )

            // 2. 초기 선택값 있음
            var selected2 by remember { mutableStateOf("부산") }
            BasicExposedDropdown(
                items = listOf("서울", "부산", "광주"),
                selectedOption = selected2,
                onItemSelected = { selected2 = it }
            )

            // 3. 선택 불가능 (disabled)
            var selected3 by remember { mutableStateOf<String?>(null) }
            BasicExposedDropdown(
                hint = "선택 비활성화",
                items = listOf("A", "B", "C"),
                selectedOption = selected3,
                onItemSelected = { selected3 = it },
                enabled = false
            )

            // 4. 빈 리스트
            var selected4 by remember { mutableStateOf<String?>(null) }
            BasicExposedDropdown(
                hint = "리스트 없음",
                items = emptyList(),
                selectedOption = selected4,
                onItemSelected = { selected4 = it }
            )

            // 5. 긴 텍스트 확인
            var selected5 by remember { mutableStateOf<String?>(null) }
            BasicExposedDropdown(
                hint = "긴 텍스트 확인",
                items = listOf(
                    "짧은 텍스트",
                    "중간 길이의 텍스트",
                    "이것은 매우 길고 넓은 드롭다운 텍스트입니다. 너비를 확인하세요"
                ),
                selectedOption = selected5,
                onItemSelected = { selected5 = it }
            )

            // 6. 복수 드롭다운 (도시 + 카테고리)
            var selectedCity by remember { mutableStateOf<String?>(null) }
            var selectedCategory by remember { mutableStateOf<String?>(null) }

            BasicExposedDropdown(
                hint = "도시 선택",
                items = listOf("서울", "부산", "대전", "제주"),
                selectedOption = selectedCity,
                onItemSelected = { selectedCity = it }
            )

            BasicExposedDropdown(
                hint = "카테고리 선택",
                items = listOf("음식", "여행", "취미", "기타"),
                selectedOption = selectedCategory,
                onItemSelected = { selectedCategory = it }
            )
        }
    }
}
