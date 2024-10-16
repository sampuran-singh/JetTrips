package com.example.jettrips.ui.screens.home.travelblogs


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jettrips.model.TravelBlog
import com.example.jettrips.ui.components.JetTripImageView
import com.example.jettrips.ui.screens.home.HeaderComponent
import com.example.jettrips.ui.screens.home.HomeUiState
import com.example.jettrips.ui.screens.home.HomeViewModel
import com.example.jettrips.ui.theme.JetTripsTheme

@Composable
fun TravelBlogCard(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by homeViewModel.blogsUiState

    Column(modifier = modifier.padding(16.dp)) {
        HeaderComponent(title = "Get inspiration for next trip")

        when (uiState) {
            is HomeUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            is HomeUiState.Success -> {
                val destinations = (uiState as HomeUiState.Success).data as List<TravelBlog>
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    items(destinations) { destination ->
                        TravelBlogCard(blog = destination)
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
fun TravelBlogCard(blog: TravelBlog) {
    Box(
        modifier = Modifier
            .width(310.dp)
            .height(210.dp)
            .padding(end = 8.dp)
    ) {
        blog.img?.let {
            JetTripImageView(
                url = it,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(8.dp)
        ) {
            Text(
                text = blog.name,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                color = Color.White
            )
            Text(
                text = blog.description,
                maxLines = 2,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White
            )
        }
    }
}


@Preview
@Composable
fun DestinationPreview() {
    JetTripsTheme {
        TravelBlogCard(
            TravelBlog(
                "TripAdvisor",
                "https://www.tripadvisor.com",
                "https://www.tripadvisor.com",
                "TripAdvisor is a popular travel website offering user-generated reviews and ratings for hotels, restaurants, attractions, and experiences worldwide.",
            )
        )
    }
}


@Preview
@Composable
fun ExplorePreview() {
    JetTripsTheme {
        TravelBlogCard(Modifier)
    }
}