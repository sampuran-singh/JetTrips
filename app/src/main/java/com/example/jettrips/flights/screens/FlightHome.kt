package com.example.jettrips.flights.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.jettrips.ui.theme.JetTripsTheme
import com.example.jettrips.utils.JetTripsTopAppBar


@Composable
fun FlightHome(
    modifier: Modifier,
    onBookFlightClicked: () -> Unit,
    navController: NavHostController = rememberNavController()
) {
    JetTripsTheme(dynamicColor = false) {
        Scaffold(topBar = {
            JetTripsTopAppBar {
                //Handle back click
                navController.popBackStack()
            }
        },
            content = { innerPadding ->
                FlightBooking(modifier.padding(innerPadding), {
                    onBookFlightClicked()
                })
            })
    }
}

@Preview
@Composable
fun FlightHomePreview() {
    JetTripsTheme {
        FlightHome(modifier = Modifier, {})
    }
}
