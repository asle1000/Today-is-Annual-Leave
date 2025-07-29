package com.dayoff.core.ui.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dayoff.core.ui.R
import com.dayoff.core.ui.utils.CircleRippleButton
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.designsystem.theme.LocalTialTypes

@Composable
fun CalendarHeader(
    modifier: Modifier = Modifier,
    year: Int,
    month: Int,
    onPrevMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onRefresh: () -> Unit,
) {
    val type = LocalTialTypes.current
    val color = LocalTialColors.current

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            CircleRippleButton(
                contentPadding = 4.dp,
                clickable = onPrevMonth,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "icon previous button",
                )
            }

            Spacer(modifier = Modifier.size(size = 6.dp))

            Text(
                text = "$year. ${month.toString().padStart(2, '0')}",
                color = color.text.surface.primary,
                style = type.labelLarge,
            )

            Spacer(modifier = Modifier.size(size = 6.dp))

            CircleRippleButton(
                contentPadding = 4.dp,
                clickable = onNextMonth,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_forward),
                    contentDescription = "icon forward button",
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        CircleRippleButton(
            contentPadding = 4.dp,
            clickable = onRefresh,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_refresh),
                contentDescription = "icon refresh button",
            )
        }
    }
}