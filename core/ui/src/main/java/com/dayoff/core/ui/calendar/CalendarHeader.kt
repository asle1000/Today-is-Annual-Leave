package com.dayoff.core.ui.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dayoff.core.ui.R
import com.dayoff.core.ui.utils.CircleRippleButton
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.designsystem.theme.LocalTialTypes

@Composable
fun CalendarHeader(
    modifier: Modifier = Modifier,
    month: Int,
    onMonthChange: (offset: Int) -> Unit = {},
) {
    val type = LocalTialTypes.current
    val color = LocalTialColors.current

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            CircleRippleButton(
                contentPadding = 4.dp,
                clickable = { onMonthChange(-1) },
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = "Previous month",
                )
            }

            Text(
                modifier = Modifier.width(width = 70.dp),
                text = "${month}월",
                color = color.text.surface.primary,
                style = type.labelLarge,
                textAlign = TextAlign.Center,
            )

            CircleRippleButton(
                contentPadding = 4.dp,
                clickable = { onMonthChange(1) },
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_forward),
                    contentDescription = "Next month",
                )
            }
        }

        Spacer(Modifier.weight(1f))
    }
}