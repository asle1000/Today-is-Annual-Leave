package com.dayoff.designsystem.components.previews

import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.dayoff.designsystem.components.TialBasicTextField

@Preview(showBackground = true, name = "기본 상태")
@Composable
fun PreviewTialTextField_Default() {
    var text by remember { mutableStateOf("") }

    TialBasicTextField(
        value = text,
        onValueChange = { text = it },
        placeholder = "이름을 입력하세요"
    )
}

@Preview(showBackground = true, name = "입력 중 (포커스)")
@Composable
fun PreviewTialTextFieldFocused() {
    var text by remember { mutableStateOf("홍길") }
    val interactionSource = remember { MutableInteractionSource() }

    // 포커스 상태 흉내내기
    LaunchedEffect(Unit) {
        interactionSource.tryEmit(FocusInteraction.Focus())
    }

    TialBasicTextField(
        value = text,
        onValueChange = { text = it },
        placeholder = "이름을 입력하세요",
        interactionSource = interactionSource
    )
}

@Preview(showBackground = true, name = "에러 상태")
@Composable
fun PreviewTialTextFieldError() {
    var text by remember { mutableStateOf("너무 긴 입력입니다") }

    Column {
        TialBasicTextField(
            value = text,
            onValueChange = { text = it },
            placeholder = "이름을 입력하세요",
            isError = true
        )
    }
}

@Preview(showBackground = true, name = "비활성화 상태")
@Composable
fun PreviewTialTextFieldDisabled() {
    TialBasicTextField(
        value = "변경 불가",
        onValueChange = {},
        enabled = false,
        placeholder = "입력 불가"
    )
}
