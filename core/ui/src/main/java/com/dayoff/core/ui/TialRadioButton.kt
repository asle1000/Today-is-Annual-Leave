package com.dayoff.core.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.designsystem.theme.LocalTialTypes

/**
 *  Created by KyunghyunPark at 2025. 8. 6.

 */
@Composable
fun TialRadioButton(
    modifier: Modifier = Modifier,
    checked: Boolean,
    title: String,
    content: String,
    onClick: () -> Unit = {},
) {
    val colors = LocalTialColors.current
    val typography = LocalTialTypes.current

    val shape = RoundedCornerShape(24.dp)
    val borderColor = if (checked) colors.border.brand.primary else colors.border.surface.secondary
    val backgroundColor = if (checked) colors.background.brand.overlay else colors.background.base.white
    val iconRes = if (checked) R.drawable.ic_radio_checked else R.drawable.ic_radio_unchecked

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = shape)
            .border(width = 1.dp, color = borderColor, shape = shape)
            .clickable(onClick = onClick),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "radio_button",
                tint = Color.Unspecified,
            )

            Spacer(modifier = Modifier.width(width = 12.dp))

            Column(modifier = Modifier.weight(weight = 1f)) {
                Text(
                    text = title,
                    style = typography.titleMedium.copy(color = colors.text.surface.primary)
                )
                Text(
                    text = content,
                    style = typography.bodyMedium.copy(color = colors.text.surface.tertiary)
                )
            }
        }
    }
}

@Composable
fun TialRadioButtonGroup(
    options: List<Pair<String, String>>,
    modifier: Modifier = Modifier,
    initialSelectedIndex: Int = 0,
    onSelectionChanged: (Int) -> Unit = {}
) {
    var selectedIndex by remember { mutableIntStateOf(initialSelectedIndex) }

    Column(modifier = modifier) {
        options.forEachIndexed { index, (title, content) ->
            TialRadioButton(
                title = title,
                content = content,
                checked = selectedIndex == index,
                onClick = {
                    selectedIndex = index
                    onSelectionChanged(index)
                },
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun TialRadioButtonGroupPreview() {
    val options = listOf(
        "연차 소진" to "사용 가능한 연차 일수가 줄어듭니다.",
        "연차 미소진" to "사용 가능한 연차는 그대로! 기록으로만 남겨요.",
    )

    TialRadioButtonGroup(
        options = options,
        onSelectionChanged = { _ ->
        }
    )
}