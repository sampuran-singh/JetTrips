package com.example.jettrips.flights.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.jettrips.utils.JetTripsButton
import com.example.jettrips.utils.JetTripsTextField

enum class CABIN_CLASS(val text: String) {
    ECONOMY("Economy"), PREMIUM_ECONOMY("Premium Economy"), BUSINESS("Business"), FIRST_CLASS("First Class")
}

data class Passenger(var adult: Int, var children: Int)

@Composable
fun PassengerBottomsheet(onDone: (Passenger) -> Unit) {
    var cabinClass by remember { mutableStateOf(CABIN_CLASS.ECONOMY) }
    var passengers by remember { mutableStateOf(Passenger(0, 0)) }

    Column(Modifier.padding(start = 16.dp, end = 16.dp, bottom = 30.dp)) {
        Text(
            text = "Add passengers",
            color = Color.Black,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )
        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 12.dp)
        )
        Text(
            text = "Travellers",
            color = Color.Black,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )
        PassengerComponent("Adults", passengers.adult) {
            passengers.adult = it
        }
        PassengerComponent("Children", passengers.children) {
            passengers.children = it
        }

        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = "Cabin Class",
            color = Color.Black,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Column() {
            CabinClassType(cabinClass, CABIN_CLASS.ECONOMY) {
                cabinClass = CABIN_CLASS.ECONOMY
            }

            Spacer(modifier = Modifier.width(8.dp))


            CabinClassType(cabinClass, CABIN_CLASS.PREMIUM_ECONOMY) {
                cabinClass = CABIN_CLASS.ECONOMY
            }
            Spacer(modifier = Modifier.width(8.dp))


            CabinClassType(cabinClass, CABIN_CLASS.BUSINESS) {
                cabinClass = CABIN_CLASS.BUSINESS
            }
            Spacer(modifier = Modifier.width(8.dp))

            CabinClassType(cabinClass, CABIN_CLASS.FIRST_CLASS) {
                cabinClass = CABIN_CLASS.FIRST_CLASS
            }
        }

        JetTripsButton(text = "Done", enabled = true) { onDone(passengers) }

    }
}


@Composable
fun PassengerComponent(text: String, passenger: Int, onPassengerUpdate: (Int) -> Unit) {
    var passengerCount by remember { mutableIntStateOf(passenger) }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.weight(1f))

        JetTripsTextField(
            value = passengerCount.toString(), onValueChange = {},
            enabled = false,
            readOnly = true,
            modifier = Modifier
                .width(120.dp)
                .padding(0.dp),
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(0.dp),
                    onClick = {
                        if (passengerCount > 0) {
                            passengerCount--
                            onPassengerUpdate(passengerCount)
                        }
                    }
                ) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "Decrease Passengers",
                        modifier = Modifier
                            .size(24.dp)
                            .padding(0.dp)
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(0.dp),

                    onClick = {
                        if (passengerCount < 10) {
                            passengerCount++
                            onPassengerUpdate(passengerCount)
                        }
                    }
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Increase Passengers",
                        modifier = Modifier
                            .size(24.dp)
                            .padding(0.dp)
                    )

                }
            },
            textStyle = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun CabinClassType(selectedCabinClass: CABIN_CLASS, label: CABIN_CLASS, onClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = selectedCabinClass == label,
            onClick = onClick,
            colors = RadioButtonColors(
                selectedColor = Color.Blue,
                unselectedColor = Color.Black,
                disabledSelectedColor = Color.Black,
                disabledUnselectedColor = Color.Black
            )
        )
        Text(
            text = label.text,
            modifier = Modifier.padding(start = 4.dp),
            color = Color.Black,
            style = MaterialTheme.typography.bodySmall
        )
    }
}