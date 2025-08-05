package com.dayoff.feature.calendar.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.feature.calendar.R

@Composable
fun AddAnnualEventFab(onClick: () -> Unit) {
    val color = LocalTialColors.current
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = color.button.brand.background.primary,
        contentColor = color.button.brand.icon.primaryOn,
        shape = CircleShape,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_add),
            tint = color.button.brand.icon.primaryOn,
            contentDescription = "Large floating action button"
        )
    }
}