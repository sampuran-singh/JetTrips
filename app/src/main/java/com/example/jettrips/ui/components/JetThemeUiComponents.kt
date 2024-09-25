package com.example.jettrips.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter


@Composable
fun JetTripTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    contentDescription: String,
    painter: Painter,
    modifier: Modifier,
    onIconClick: () -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        leadingIcon = {
            IconButton(onClick = onIconClick) {
                Icon(
                    painter = painter,
                    contentDescription = contentDescription
                )
            }
        },
        modifier = modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Blue,
            focusedBorderColor = Color.Blue,
            focusedLabelColor = Color.Blue,
            unfocusedLabelColor = Color.Black
        ),
        keyboardOptions = keyboardOptions
    )
}