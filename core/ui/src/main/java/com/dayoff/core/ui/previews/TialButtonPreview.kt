package com.dayoff.core.ui.previews

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dayoff.core.ui.button.TialButton
import com.dayoff.core.ui.button.TialOutlineButton

@Preview(showBackground = true)
@Composable
fun TialButtonPreview() {
    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Row {
            TialButton(text = "Button", enabled = true, onClick = {})
            Spacer(Modifier.width(8.dp))
            TialButton(text = "Button", enabled = true, isLoading = true,onClick = {})
        }

        Spacer(Modifier.height(16.dp))

        TialButton(text = "Button", enabled = false, onClick = {})

        Spacer(Modifier.height(16.dp))

        TialButton(
            modifier = Modifier.fillMaxWidth(), text = "Button", enabled = true, onClick = {})

        Spacer(Modifier.height(16.dp))

        TialButton(
            modifier = Modifier.fillMaxWidth(), text = "Button", isLoading = true,enabled = true, onClick = {})


        Spacer(Modifier.height(16.dp))

        TialButton(
            modifier = Modifier.fillMaxWidth(), text = "Button", enabled = false, onClick = {})

        Spacer(Modifier.height(16.dp))

        Row {
            TialOutlineButton(text = "Button", enabled = true, onClick = {})
            Spacer(Modifier.width(8.dp))
            TialOutlineButton(text = "Button", enabled = true, isLoading = true, onClick = {})
        }


        Spacer(Modifier.height(16.dp))

        TialOutlineButton(text = "Button", enabled = false, onClick = {})

        Spacer(Modifier.height(16.dp))

        TialOutlineButton(
            modifier = Modifier.fillMaxWidth(), text = "Button", enabled = true, onClick = {})

        Spacer(Modifier.height(16.dp))

        TialOutlineButton(
            modifier = Modifier.fillMaxWidth(), text = "Button", enabled = false, onClick = {})
    }
}