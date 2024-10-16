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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.jettrips.R
import com.example.jettrips.ui.theme.JetTripsTheme
import com.example.jettrips.utils.JetTripsButton
import com.example.jettrips.utils.JetTripsTextField
import com.example.jettrips.utils.convertMillisToDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightBooking(modifier: Modifier, onBookFlightClicked: () -> Unit) {
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
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Flight Booking",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )

        Row (verticalAlignment = Alignment.CenterVertically) {
            FlightType("One Way", tripType) {
                tripType = "One Way"
            }

            Spacer(modifier = Modifier.width(16.dp))

            FlightType("Round Trip", tripType) {
                tripType = "Round Trip"
            }
        }

        // Start destination
        JetTripsTextField(
            value = startDestination,
            onValueChange = { startDestination = it },
            label = { Text(text = "Start Destination") },
            modifier = Modifier,
            leadingIcon = {
                IconButton(onClick = { showDepartureDatePicker = !showDepartureDatePicker }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_flight_takeoff),
                        contentDescription = "Select date"
                    )
                }
            },
        )

        JetTripsTextField(
            value = endDestination,
            onValueChange = { endDestination = it },
            label = { Text(text = "End Destination") },
            modifier = Modifier,
            leadingIcon = {
                IconButton(onClick = { showReturnDatePicker = !showReturnDatePicker }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_flight_land),
                        contentDescription = "Select date"
                    )
                }
            },
        )

        FlightDateSelection(
            selectedDepartureDate = selectedDepartureDate,
            label = "Departure Date",
            showDepartureDatePicker = showDepartureDatePicker,
            departureDatePickerState,
            onValueChange = { startDestination = it },
            onPopUpDismiss = { showDepartureDatePicker = false },
            onClick = { showDepartureDatePicker = !showDepartureDatePicker }
        )


        // Return date picker (only for round trip)
        if (tripType == "Round Trip") {
            FlightDateSelection(
                selectedDepartureDate = selectedReturnDate,
                label = "Return Date",
                showDepartureDatePicker = showDepartureDatePicker,
                returnDatePickerState,
                onValueChange = { endDestination = it },
                onPopUpDismiss = { showReturnDatePicker = false },
                onClick = { showReturnDatePicker = !showReturnDatePicker }
            )
        }

        JetTripsTextField(
            value = passengers,
            onValueChange = { passengers = it },
            label = { Text(text = "Number of Passengers") },
            modifier = Modifier,
            leadingIcon = {
                IconButton(onClick = { showDepartureDatePicker = !showDepartureDatePicker }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_passenger),
                        contentDescription = "Select date"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )


        JetTripsButton(text = "Book Flight", enabled = isFormComplete) { onBookFlightClicked() }
    }
}

@Composable
fun FlightType(label: String, tripType: String, onClick: () -> Unit) {
    RadioButton(
        selected = tripType == label,
        onClick = onClick,
        colors = RadioButtonColors(
            selectedColor = Color.Blue,
            unselectedColor = Color.Black,
            disabledSelectedColor = Color.Black,
            disabledUnselectedColor = Color.Black
        )
    )
    Text(text = label, modifier = Modifier.padding(start = 4.dp), color = Color.Black)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightDateSelection(
    selectedDepartureDate: String,
    label: String,
    showDepartureDatePicker: Boolean,
    departureDatePickerState: DatePickerState,
    contentDescription: String = "Select date",
    onValueChange: (String) -> Unit,
    onClick: () -> Unit,
    onPopUpDismiss: () -> Unit,
) {
    OutlinedTextField(
        value = selectedDepartureDate,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        leadingIcon = {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = contentDescription
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Blue,
            focusedBorderColor = Color.Blue,
            focusedLabelColor = Color.Blue,
            unfocusedLabelColor = Color.Black
        ),
        modifier = Modifier.fillMaxWidth(),
        readOnly = true
    )

    if (showDepartureDatePicker) {
        Popup(
            onDismissRequest = onPopUpDismiss,
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
                    showModeToggle = false,
                    colors = DatePickerDefaults.colors()
                        .copy(
                            containerColor = Color.Blue,
                            todayDateBorderColor = Color.Black,
                            todayContentColor = Color.Black,
                            selectedDayContentColor = Color.Blue
                        )
                )
            }
        }
    }
}

@Preview
@Composable
fun FlightBookingView() {
    JetTripsTheme(dynamicColor = false) {
        FlightBooking(modifier = Modifier) {
        }
    }
}
