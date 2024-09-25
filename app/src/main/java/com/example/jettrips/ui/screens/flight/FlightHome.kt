package com.example.jettrips.ui.screens.flight

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jettrips.ui.theme.JetTripsTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightHome(modifier: Modifier, onBookFlightClicked: () -> Unit) {
    JetTripsTheme(dynamicColor = false) {
        FlightBooking(modifier) {
            onBookFlightClicked()
        }
    }
}

@Preview
@Composable
fun FlightHomePreview() {
    JetTripsTheme {
        FlightHome(modifier = Modifier) {
        }
    }
}
