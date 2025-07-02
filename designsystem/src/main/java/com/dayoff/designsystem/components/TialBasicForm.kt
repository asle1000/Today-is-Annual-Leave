package com.dayoff.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dayoff.designsystem.R
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.designsystem.theme.LocalTialShapes
import com.dayoff.designsystem.theme.LocalTialTypes

@Preview
@Composable
fun TialBaseFormPreview() {
    val info = """
        입사년도를 입력하면 연차를 자동으로 계산해드려요! 
        직접 입력하고 싶다면 생략하셔도 괜찮아요
    """.trimIndent()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        var selected1 by remember { mutableStateOf<String?>("\uD83C\uDF40") }
        var selected2 by remember { mutableStateOf<String?>(null) }

        TialBasicForm(modifier = Modifier.padding(horizontal = 12.dp), label = "연도 선택", isRequired = true, "에러메시지를 여기에 쓰면 에러일 때 보이겠지", info) {

            Row() {
                BasicExposedDropdown(
                    items = listOf("\uD83C\uDF40", "\uD83C\uDF40", "\uD83C\uDF40",),
                    selectedOption = selected1,
                    onItemSelected = { selected1 = it }
                )

                Spacer(modifier = Modifier.width(16.dp))

                BasicExposedDropdown(
                    modifier = Modifier.fillMaxWidth(),
                    hint = "연도 선택",
                    items = listOf("2025", "2026", "2027", "2028"),
                    selectedOption = selected2,
                    onItemSelected = { selected2 = it }
                )
            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        TialBasicForm(label = null, isRequired = true, errorMessage = "error", infoMessage = info) {

        }

        Spacer(modifier = Modifier.height(8.dp))

        TialBasicForm(label = null, isRequired = true, errorMessage = null, infoMessage = info) {

        }
    }
}

/**
 *  Created by KyunghyunPark at 2025. 6. 30.

 */
@Composable
fun TialBasicForm(
    modifier: Modifier = Modifier,
    label: String?,
    isRequired: Boolean = false,
    errorMessage: String? = null,
    infoMessage: String? = null,
    content: @Composable () -> Unit
) {
    Column(horizontalAlignment = Alignment.Start, modifier = modifier) {
        label?.let { label ->
            FormLabel(
                label = label,
                isRequired = isRequired,
                modifier = Modifier.padding(start = 4.dp),
            )

            Spacer(modifier = Modifier.height(4.dp))
        }

        content()

        errorMessage?.let { msg ->
            FormErrorGuide(modifier = Modifier.padding(start = 4.dp), message = msg)
        }

        infoMessage?.let {
            FormInformation(Modifier.padding(top = 12.dp))
        }
    }
}

@Composable
private fun FormErrorGuide(
    modifier: Modifier = Modifier,
    message: String,
) {
    val color = LocalTialColors.current
    val type = LocalTialTypes.current

    Text(
        text = message,
        style = type.bodySmall,
        color = color.text.danger.primary,
        modifier = modifier
    )
}

@Composable
private fun FormLabel(
    modifier: Modifier = Modifier,
    label: String,
    isRequired: Boolean,
) {
    val color = LocalTialColors.current
    val type = LocalTialTypes.current

    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = color.text.surface.tertiary)) {
                append(label)
            }

            if (isRequired) {
                withStyle(style = SpanStyle(color = color.text.danger.primary)) {
                    append(" *")
                }
            }

        },
        style = type.titleMedium,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun FormInformation(
    modifier: Modifier = Modifier,
    comment: String = """
        입사년도를 입력하면 연차를 자동으로 계산해드려요! 
        직접 입력하고 싶다면 생략하셔도 괜찮아요
    """.trimIndent(),
) {
    val color = LocalTialColors.current
    val type = LocalTialTypes.current
    val shape = LocalTialShapes.current

    Row(
        modifier = modifier
            .clip(shape = shape.Small)
            .background(color = color.background.brand.tertiary)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_app_icon),
            contentDescription = "image_app_icon"
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(text = comment, style = type.bodyMedium, color = color.text.surface.tertiary)
    }
}
