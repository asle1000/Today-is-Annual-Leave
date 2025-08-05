package com.dayoff.feature.calendar.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dayoff.core.ui.button.CircleButton
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.designsystem.theme.LocalTialTypes
import com.dayoff.feature.calendar.R

/**
 *  Created by KyunghyunPark at 2025. 8. 2.

 */
@Composable
private fun AddAnnualYearButton(onClick: () -> Unit = {}) {
    val color = LocalTialColors.current

    CircleButton(
        borderColor = color.border.surface.secondary,
        backgroundColor = color.background.surface.primary,
        onClick = onClick
    ) {
        Image(
            modifier = Modifier.align(Alignment.Center),
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = "연차 추가"
        )
    }
}

@Composable
private fun AnnualYearButton(
    text: String, selected: Boolean = true, onClick: () -> Unit = {}
) {
    val color = LocalTialColors.current
    val style = LocalTialTypes.current

    val borderColor = if (selected) color.border.brand.primary else color.border.surface.secondary
    val backgroundColor =
        if (selected) color.background.brand.tertiary else color.background.surface.primary
    val textColor = color.text.surface.secondary

    CircleButton(
        borderColor = borderColor, backgroundColor = backgroundColor, onClick = onClick
    ) {
        Text(
            modifier = Modifier.align(alignment = Alignment.Center),
            text = text,
            style = style.labelLarge.copy(color = textColor)
        )
    }
}


@Composable
fun AnnualYearButtonGroup(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    annualYearList: List<String>,
    selectIdx: Int = 0,
    onAdded: () -> Unit = {},
    onSelected: (Int) -> Unit = { _ -> }
) {
    LazyRow(
        modifier = modifier,
        state = state,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 12.dp)
    ) {

        item {
            AddAnnualYearButton(onClick = onAdded)
        }

        items(annualYearList.size) { idx ->
            val item = annualYearList[idx]
            AnnualYearButton(text = item, selected = idx == selectIdx) {
                onSelected(idx)
            }
        }
    }
}