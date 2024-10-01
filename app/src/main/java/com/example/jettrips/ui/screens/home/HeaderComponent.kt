package com.example.jettrips.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HeaderComponent(title: String, subtitle: String? = null) {
    Column(Modifier.padding(8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black
        )
        subtitle?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
        }
    }
}