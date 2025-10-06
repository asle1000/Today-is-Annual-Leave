package com.dayoff.core.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dayoff.core.model.AnnualLeaveRecord
import com.dayoff.core.ui.R
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.designsystem.theme.LocalTialShapes
import com.dayoff.designsystem.theme.LocalTialTypes

/**
 *  Created by KyunghyunPark at 2025. 10. 6.

 */
@Preview
@Composable
fun StampView(
    modifier: Modifier = Modifier,
    itemList: List<AnnualLeaveRecord> = emptyList(),
    columns: Int = 5
) {
    val color = LocalTialColors.current
    val typo = LocalTialTypes.current
    val shape = LocalTialShapes.current

    val pagerState = rememberPagerState(0) {
        2
    }

    val totalLeaveCnt = itemList

    Column {

        HorizontalPager(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.Yellow),
            state = pagerState,
        ) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp, horizontal = 10.dp),
                maxItemsInEachRow = columns,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                itemList.mapIndexed { idx, record ->

                    StampItem(text = it.toString())
                }
            }


        }

        Spacer(modifier = Modifier.weight(1.0f))

        Text("TEST", modifier = Modifier.padding(vertical = 12.dp))
    }


}

@Preview
@Composable
fun StampItem(
    modifier: Modifier = Modifier,
    text: String = "20",
) {
    val color = LocalTialColors.current
    val typo = LocalTialTypes.current

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Icon(
            painter = painterResource(id = R.drawable.ic_leave_clover),
            tint = color.leave.annual,
            contentDescription = null
        )

        Text(
            text, style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 36.sp,
                fontFamily = FontFamily(Font(resId = com.dayoff.designsystem.R.font.y_clover_bold)),
                color = color.background.base.white
            )
        )
    }
}
