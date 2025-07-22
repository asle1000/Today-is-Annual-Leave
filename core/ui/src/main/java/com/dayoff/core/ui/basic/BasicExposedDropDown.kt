package com.dayoff.core.ui.basic

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.dayoff.designsystem.R
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.designsystem.theme.LocalTialShapes
import com.dayoff.designsystem.theme.LocalTialTypes

/**
 * Basic exposed dropdown
 *
 * @param modifier
 * @param hint
 * @param items
 * @param selectedOption
 * @param onItemSelected
 * @param enabled
 * @param isError
 * @receiver
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicExposedDropdown(
    modifier: Modifier = Modifier,
    hint: String? = null,
    items: List<String> = emptyList(),
    selectedOption: String?,
    onItemSelected: (String) -> Unit,
    enabled: Boolean = true,
    isError: Boolean = false,
) {
    val color = LocalTialColors.current
    val type = LocalTialTypes.current
    val shape = LocalTialShapes.current

    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()

    val longestItem = items.takeIf { it.isNotEmpty() } ?: listOf("목록 없음")
    val defaultSelected = hint ?: longestItem.first()
    val currentSelection = selectedOption ?: defaultSelected

    val textToMeasure = (listOfNotNull(hint) + items).maxByOrNull { it.length } ?: "목록 없음"
    val longestTextSize by remember(textToMeasure) {
        derivedStateOf {
            textMeasurer.measure(
                text = AnnotatedString(textToMeasure),
                style = type.bodyLarge
            ).size
        }
    }

    val longestItemSizeDp = with(density) {
        DpSize(width = longestTextSize.width.toDp(), height = longestTextSize.height.toDp())
    }

    var expanded by remember { mutableStateOf(false) }
    val rotation = remember { Animatable(0f) }
    val isEnabled = enabled && items.isNotEmpty()

    LaunchedEffect(expanded) {
        rotation.animateTo(
            targetValue = if (expanded) -180f else 0f,
            animationSpec = tween(durationMillis = 200)
        )
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            if (isEnabled) expanded = !expanded
            else Log.d("tial", "Dropdown disabled or empty")
        }
    ) {
        Box(
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                .clip(shape.Small)
                .border(
                    width = 1.dp,
                    color = when {
                        isError -> color.border.danger.primary
                        !isEnabled -> color.background.disabled.primary
                        expanded -> color.border.brand.primary
                        else -> color.border.base.input
                    },
                    shape = shape.Small
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val isInPreview = LocalInspectionMode.current
                val iconPainter = if (isInPreview) {
                    painterResource(id = android.R.drawable.arrow_down_float)
                } else {
                    rememberVectorPainter(ImageVector.vectorResource(R.drawable.ic_dropdown_20dp))
                }

                Text(
                    text = currentSelection,
                    style = type.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .width(longestItemSizeDp.width + 4.dp),
                    color = when {
                        !isEnabled -> color.text.disabled.primary
                        expanded || currentSelection == hint -> color.text.surface.placeholder
                        else -> color.text.surface.primary
                    }
                )

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    modifier = Modifier.rotate(rotation.value),
                    painter = iconPainter,
                    tint = if (isEnabled) color.icon.surface.secondary else color.icon.disabled.primary,
                    contentDescription = "arrow drop down and up"
                )
            }
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            shadowElevation = 0.dp,
            shape = shape.Small,
            containerColor = Color.Transparent,
            modifier = Modifier
                .wrapContentWidth()
                .padding(bottom = 4.dp)
                .background(color.background.base.white)
                .offset(y = 4.dp)
                .clip(shape.Small)
                .border(
                    width = 1.dp,
                    color = color.border.surface.secondary,
                    shape = shape.Small
                )
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = item,
                            style = type.labelMedium,
                            color = color.text.surface.secondary,
                        )
                    },
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}