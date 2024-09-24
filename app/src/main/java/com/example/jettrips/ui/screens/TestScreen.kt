package com.example.jettrips.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen(modifier: Modifier) {

    Column {
        MixComponent(modifier = modifier, text = "1ST component")
        MixComponent(modifier = Modifier, text = "2ST component")

    }


}

@Composable
fun MixComponent(modifier: Modifier, text: String) {
    val context = LocalContext.current
    Box(modifier = modifier) {
        Text(text = "1st text")
        ButtonComposable(
            modifier.padding(30.dp),
            text,
            onClick = {
                Toast.makeText(context, "Button clicked", Toast.LENGTH_LONG)
            })

    }

}

@Composable
fun ButtonComposable(modifier: Modifier, text: String, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text = "$text")
    }

}


