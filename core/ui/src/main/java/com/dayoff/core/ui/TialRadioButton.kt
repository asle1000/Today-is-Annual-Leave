package com.dayoff.core.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.designsystem.theme.LocalTialTypes

/**
 *  Created by KyunghyunPark at 2025. 8. 6.

 */
@Composable
fun TialTextRadioButton(
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
    val backgroundColor =
        if (checked) colors.background.brand.overlay else colors.background.base.white
    val iconRes = if (checked) R.drawable.ic_radio_checked else R.drawable.ic_radio_unchecked

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = shape)
            .border(width = 1.dp, color = borderColor, shape = shape)
            .clickable(onClick = onClick, role = Role.RadioButton),
        color = backgroundColor,
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
fun TialIconRadioButton(
    modifier: Modifier = Modifier,
    checked: Boolean,
    iconRes: Int,
    label: String,
    onClick: () -> Unit = {},
) {
    val colors = LocalTialColors.current
    val typography = LocalTialTypes.current

    val shape = RoundedCornerShape(24.dp)
    val borderColor = if (checked) colors.border.brand.primary else colors.border.surface.secondary
    val backgroundColor = if (checked) colors.background.brand.overlay else Color.Transparent

    Surface(
        modifier = modifier
            .clip(shape = shape)
            .border(width = 1.dp, color = borderColor, shape = shape)
            .clickable(onClick = onClick, role = Role.RadioButton),
        color = backgroundColor,
    ) {
        Column(
//            modifier = Modifier.padding(vertical = 20.dp, horizontal = 34.dp),
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "radio_button",
                tint = Color.Unspecified,
            )

            Spacer(modifier = Modifier.height(height = 8.dp))

            Text(
                text = label,
                style = typography.labelMedium.copy(color = colors.text.surface.primary),
                textAlign = TextAlign.Center,
            )
        }
    }
}


@Composable
fun TialTextRadioButtonVerticalGroup(
    options: List<Pair<String, String>>,
    modifier: Modifier = Modifier,
    initialSelectedIndex: Int = 0,
    onSelectionChanged: (Int) -> Unit = {}
) {
    var selectedIndex by remember { mutableIntStateOf(initialSelectedIndex) }

    Column(modifier = modifier) {
        options.forEachIndexed { index, (title, content) ->
            TialTextRadioButton(
                title = title, content = content, checked = selectedIndex == index, onClick = {
                    selectedIndex = index
                    onSelectionChanged(index)
                }, modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
fun TialIconRadioButtonHorizontalGroup(
    modifier: Modifier = Modifier,
    options: List<Pair<Int, String>>,
    initialSelectedIndex: Int = 0,
    onSelectionChanged: (Int) -> Unit = {}
) {
    var selectedIndex by remember { mutableIntStateOf(initialSelectedIndex) }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        options.forEachIndexed { index, (img, label) ->
            TialIconRadioButton(
                modifier = Modifier.weight(1f),
                iconRes = img,
                label = label,
                checked = selectedIndex == index,
                onClick = {
                    selectedIndex = index
                    onSelectionChanged(index)
                },
            )

            if (index != options.lastIndex) {
                Spacer(modifier = Modifier.width(width = 8.dp))
            }
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

    TialTextRadioButtonVerticalGroup(
        options = options, onSelectionChanged = { _ ->
        })
}

@Preview(showBackground = true)
@Composable
fun TialIconRadioButtonGroupPreview() {
    val options = listOf(
        R.drawable.img_annual_leave to "연차",
        R.drawable.img_half_annual_leave to "반차",
        R.drawable.img_particle_day_leave to "시간차",
    )

    TialIconRadioButtonHorizontalGroup(options = options, onSelectionChanged = { _ -> })
}

