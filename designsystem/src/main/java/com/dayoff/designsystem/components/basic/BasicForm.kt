package com.dayoff.designsystem.components.basic

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

/**
 *  Created by KyunghyunPark at 2025. 6. 30.

 */

/**
 * Basic form
 *
 * @param modifier
 * @param label
 * @param isRequired
 * @param errorMessage
 * @param infoMessage
 * @param content
 * @receiver
 */
@Composable
fun BasicForm(
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
