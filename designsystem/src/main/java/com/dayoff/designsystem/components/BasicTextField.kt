package com.dayoff.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dayoff.designsystem.theme.LocalTialColors
import com.dayoff.designsystem.theme.LocalTialShapes
import com.dayoff.designsystem.theme.LocalTialTypes

/**
 *  Created by KyunghyunPark at 2025. 7. 2.

 */

/**
 * Basic text field
 *
 * @param modifier
 * @param value
 * @param onValueChange
 * @param placeholder
 * @param isError
 * @param enabled
 * @param readOnly
 * @param singleLine
 * @param keyboardOptions
 * @param keyboardActions
 * @param interactionSource
 * @receiver
 */
@Composable
fun BasicTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String? = null,
    isError: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val color = LocalTialColors.current
    val type = LocalTialTypes.current
    val shape = LocalTialShapes.current

    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderColor = when {
        !enabled -> color.border.disabled.primary
        isError -> color.border.danger.primary
        isFocused -> color.border.brand.primary
        else -> color.border.base.input
    }

    Surface(
        modifier = modifier
            .clip(shape.Small)
            .border(1.dp, borderColor, shape.Small)
            .background(color.background.base.white)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        color = Color.Transparent
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            singleLine = singleLine,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
            textStyle = type.bodyMedium.copy(
                color = if (enabled) color.text.surface.primary else color.text.disabled.primary
            ),
            decorationBox = @Composable { innerTextField ->
                Box {
                    if (value.isEmpty() && placeholder != null) {
                        Text(
                            text = placeholder,
                            style = type.bodyMedium,
                            color = color.text.surface.tertiary
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}
