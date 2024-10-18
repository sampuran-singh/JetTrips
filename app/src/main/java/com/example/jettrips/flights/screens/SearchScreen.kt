package com.example.jettrips.flights.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jettrips.R
import com.example.jettrips.flights.model.OperatingCity
import com.example.jettrips.flights.model.SearchViewModel
import com.example.jettrips.utils.JetTripsTextField


@Composable
fun FlightSearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    onItemSelected: (OperatingCity) -> Unit
) {

    val searchQuery by searchViewModel.searchQuery
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.grey)),
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column {
                JetTripsTextField(
                    value = searchQuery,
                    onValueChange = { searchViewModel.updateSearchQuery(it) },
                    label = { androidx.compose.material3.Text(text = "Enter airport or City") },
                    modifier = Modifier.fillMaxWidth()
                )
                val searchResults: List<OperatingCity> by searchViewModel.searchResults
                LazyColumn {
                    items(searchResults.size) { index ->
                        AirportItem(searchResults[index]) {
                            //TODO: Handle click here
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AirportItem(city: OperatingCity, onItemSelected: () -> Unit) {
    Column {
        Row {
            androidx.compose.material3.Icon(
                painter = painterResource(id = R.drawable.ic_flight_takeoff),
                contentDescription = "Select date",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = city.iataCode, modifier = Modifier.padding(end = 8.dp))
            Text(text = city.cityName)
        }
        Text(text = city.country)
    }

}