package com.dayoff.core.ui.basic

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dayoff.core.ui.R
import com.dayoff.core.ui.utils.CircleRippleButton
import com.dayoff.designsystem.theme.LocalTialTypes

/**
 *  Created by KyunghyunPark at 2025. 10. 22.

 */
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun YearSelectorHeader(
    modifier: Modifier = Modifier,
    selectedYear: Int = 2025,
    onYearSelectorClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        YearSelectorButton(
            year = selectedYear,
            onClick = onYearSelectorClick,
        )

        Spacer(modifier = Modifier.weight(1f))

        SettingsButton(onClick = onSettingsClick)
    }
}

@Composable
private fun YearSelectorButton(
    year: Int,
    onClick: () -> Unit,
) {
    val typography = LocalTialTypes.current

    Row(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 12.dp)
            .clickable(
                role = Role.Button,
                onClick = onClick,
                onClickLabel = stringResource(id = R.string.description_year_selector_button)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.year_selector_format, year),
            style = typography.titleLarge
        )

        Spacer(modifier = Modifier.width(6.dp))

        Icon(
            painter = painterResource(R.drawable.ic_selector_clover),
            tint = Color.Unspecified,
            contentDescription = stringResource(R.string.description_year_selector_icon)
        )
    }
}

@Composable
private fun SettingsButton(onClick: () -> Unit) {
    CircleRippleButton(
        contentPadding = 12.dp,
        clickable = onClick
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_settings),
            contentDescription = stringResource(R.string.description_settings_button)
        )
    }
}