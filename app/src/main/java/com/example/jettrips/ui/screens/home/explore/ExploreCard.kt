package com.example.jettrips.ui.screens.home.explore

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jettrips.model.TouristDestination
import com.example.jettrips.ui.components.JetTripImageView
import com.example.jettrips.ui.screens.home.HeaderComponent
import com.example.jettrips.ui.screens.home.HomeUiState
import com.example.jettrips.ui.screens.home.HomeViewModel
import com.example.jettrips.ui.theme.JetTripsTheme

@Composable
fun ExploreCard(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by homeViewModel.destinationUiState

    Column(modifier = modifier.padding(16.dp)) {
        HeaderComponent("Explore India", "Most visited destination")
        when (uiState) {
            is HomeUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            is HomeUiState.Success -> {
                val destinations = (uiState as HomeUiState.Success).data as List<TouristDestination>
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    items(destinations) { destination ->
                        DestinationCard(destination = destination)
                    }
                }
            }

            is HomeUiState.Error -> {
                Text(
                    text = (uiState as HomeUiState.Error).message,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun DestinationCard(destination: TouristDestination) {
    Column(
        modifier = Modifier
            .width(180.dp)
            .padding(end = 8.dp)
    ) {
        Card(
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth()
        ) {
            JetTripImageView(url = destination.img, modifier = Modifier)
        }
        Text(
            text = destination.name,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
        Text(
            text = destination.location,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Black
        )
        Text(
            text = destination.description,
            maxLines = 3,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Black
        )
    }
}

@Preview
@Composable
fun DestinationPreview() {
    JetTripsTheme {
        DestinationCard(
            TouristDestination(
                "Jaipur",
                "Rajasthan, India",
                "https://sample_jaipur.png",
                "Known as the Pink City, Jaipur is famous for its stunning forts, palaces, and vibrant markets.",
            )
        )
    }
}


@Preview
@Composable
fun ExplorePreview() {
    JetTripsTheme {
        ExploreCard(Modifier)
    }
}