package com.example.jettrips.ui.screens.flight

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.jettrips.ui.theme.JetTripsTheme
import com.example.jettrips.utils.convertMillisToDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightHome(modifier: Modifier, onBookFlightClicked: () -> Unit) {
    var tripType by remember { mutableStateOf("One Way") }
    var startDestination by remember { mutableStateOf("") }
    var endDestination by remember { mutableStateOf("") }
    var passengers by remember { mutableStateOf("") }

    var showDepartureDatePicker by remember { mutableStateOf(false) }
    var showReturnDatePicker by remember { mutableStateOf(false) }

    val departureDatePickerState = rememberDatePickerState()
    val returnDatePickerState = rememberDatePickerState()

    val selectedDepartureDate = departureDatePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    val selectedReturnDate = returnDatePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""


    val isFormComplete by remember {
        derivedStateOf {
            startDestination.isNotBlank()
                    && endDestination.isNotBlank()
                    && passengers.isNotBlank()
//                    && selectedDepartureDate.isNotBlank()
//                    && (tripType == "One Way" || (tripType == "Round Trip" && selectedReturnDate.isNotBlank()))
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Flight Booking", style = MaterialTheme.typography.bodyMedium)

        // Radio buttons for trip type
        Row {
            RadioButton(
                selected = tripType == "One Way",
                onClick = { tripType = "One Way" }
            )
            Text(text = "One Way", modifier = Modifier.padding(start = 4.dp))

            Spacer(modifier = Modifier.width(16.dp))

            RadioButton(
                selected = tripType == "Round Trip",
                onClick = { tripType = "Round Trip" }
            )
            Text(text = "Round Trip", modifier = Modifier.padding(start = 4.dp))
        }

        // Start destination
        OutlinedTextField(
            value = startDestination,
            onValueChange = { startDestination = it },
            label = { Text(text = "Start Destination") },
            modifier = Modifier.fillMaxWidth()
        )

        // End destination
        OutlinedTextField(
            value = endDestination,
            onValueChange = { endDestination = it },
            label = { Text(text = "End Destination") },
            modifier = Modifier.fillMaxWidth()
        )

        // Departure date picker
        OutlinedTextField(
            value = selectedDepartureDate,
            onValueChange = {},
            label = { Text(text = "Departure Date") },
            trailingIcon = {
                IconButton(onClick = { showDepartureDatePicker = !showDepartureDatePicker }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )

        if (showDepartureDatePicker) {
            Popup(
                onDismissRequest = { showDepartureDatePicker = false },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    DatePicker(
                        state = departureDatePickerState,
                        showModeToggle = false
                    )
                }
            }
        }


        // Return date picker (only for round trip)
        if (tripType == "Round Trip") {
            OutlinedTextField(
                value = selectedReturnDate,
                onValueChange = {},
                label = { Text(text = "Return Date") },
                trailingIcon = {
                    IconButton(onClick = { showReturnDatePicker = !showReturnDatePicker }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Select date"
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                readOnly = true
            )
            if (showReturnDatePicker) {
                Popup(
                    onDismissRequest = { showReturnDatePicker = false },
                    alignment = Alignment.TopStart
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = 64.dp)
                            .shadow(elevation = 4.dp)
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(16.dp)
                    ) {
                        DatePicker(
                            state = returnDatePickerState,
                            showModeToggle = false
                        )
                    }
                }
            }
        }

        // Number of passengers
        OutlinedTextField(
            value = passengers,
            onValueChange = { passengers = it },
            label = { Text(text = "Number of Passengers") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Book Button
        Button(
            onClick = { onBookFlightClicked() },
            enabled = isFormComplete,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Book Flight")
        }
    }
}

@Preview
@Composable
fun FlightPreview() {
    JetTripsTheme {
        FlightHome(modifier = Modifier) {
        }
    }
}
