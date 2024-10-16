package com.example.jettrips.ui.screens.home

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jettrips.R
import com.example.jettrips.ui.screens.bottombar.HomeBottomBar
import com.example.jettrips.ui.screens.home.explore.ExploreCard
import com.example.jettrips.ui.screens.home.travelblogs.TravelBlogCard
import com.example.jettrips.ui.theme.JetTripsTheme
import com.example.jettrips.ui.theme.sanComicsFontFamily
import com.example.jettrips.utils.CATEGORY_ID
import com.example.jettrips.utils.categories

@Composable
fun HomeScreen(
    modifier: Modifier,
    onCategoryClicked: (CATEGORY_ID) -> Unit,
    navController: NavController = rememberNavController()
) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.blue))
            ) {
                Text(
                    text = "Top Bar",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp),
                )
            }
        },
        bottomBar = {
            HomeBottomBar(navController = navController)
        }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(colorResource(id = R.color.white))
                .padding(it)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            categories.chunked(2).forEach { chunkedCategories ->
                Row(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    chunkedCategories.forEach { category ->
                        Category(
                            modifier = Modifier.weight(1f),
                            text = stringResource(id = category.textResId),
                            color = emptyList(),
                            painter = painterResource(id = category.iconResId),
                            size = category.size,
                            onCategoryClicked = { onCategoryClicked(category.categoryId) }
                        )
                    }
                }
            }
            ExploreCard()
            TravelBlogCard()
        }
    }
}


@Preview
@Composable
fun TravelOptionBoxPreview() {
    JetTripsTheme {
        TravelOptionBox("Hotel", painterResource(id = R.drawable.ic_homestay))
    }
}

@Composable
fun TravelOptionBox(
    title: String,
    icon: Painter,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .aspectRatio(1f)
            .clickable { /* Handle click */ },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title)
        }
    }
}

@Composable
fun Category(
    modifier: Modifier,
    text: String = "Hotel",
    color: List<Color> = emptyList(),
    painter: Painter,
    size: Dp = 64.dp,
    onCategoryClicked: () -> Unit
) {
    val categoryModifier = if (color.isNotEmpty()) {
        Modifier.background(
            Brush.linearGradient(
                colors = color,
                start = Offset(0f, 0f),
                end = Offset(1000f, 1000f)
            )
        )
    } else {
        Modifier // No background applied if the color list is empty
    }


    var isPressed by remember { mutableStateOf(false) }

    // Animate the elevation of the card
    val elevation by animateDpAsState(targetValue = if (isPressed) 16.dp else 4.dp)

    // Animate the scale of the card for a hovering effect
    val scale by animateFloatAsState(targetValue = if (isPressed) 1.1f else 1f)

    Card(
        modifier = modifier
            .fillMaxHeight()
            .padding(horizontal = 4.dp)
            .scale(scale) // Apply the animated scale
            .clickable {
                isPressed = !isPressed
                onCategoryClicked()
            },
        elevation = CardDefaults.outlinedCardElevation(
            defaultElevation = 8.0.dp,
            pressedElevation = 12.0.dp
        )
    ) {
        Row(
            modifier = categoryModifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                color = Color.Black,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painter,
                contentDescription = "Category image",
                modifier = Modifier
                    .size(size)
                    .padding(8.dp)
            )

        }
    }
}


@Composable
@Preview(showSystemUi = true, showBackground = true)
fun HomeScreenPreview() {
    JetTripsTheme {
        HomeScreen(Modifier, {})
    }
}

@Composable
@Preview
fun CategoryHotelPreview() {
    JetTripsTheme {
        Category(
            Modifier.height(100.dp),
            text = stringResource(id = R.string.category_hotel),
            color = listOf(Color.White, Color.Red),
            painter = painterResource(id = R.drawable.ic_hotels)
        ) {}
    }
}

@Composable
@Preview
fun CategoryFlightPreview() {
    JetTripsTheme {
        Category(
            Modifier.height(100.dp),
            text = stringResource(id = R.string.category_flight),
            color = listOf(Color.White, Color.Green),
            painter = painterResource(id = R.drawable.ic_flights)
        ) {}
    }
}

@Composable
@Preview
fun CategoryCabPreview() {
    JetTripsTheme {
        Category(
            Modifier.height(100.dp),
            text = stringResource(id = R.string.category_cab),
            color = listOf(Color.White, Color.Yellow),
            painter = painterResource(id = R.drawable.ic_car)
        ) {}
    }
}

@Composable
@Preview
fun CategoryTrainPreview() {
    JetTripsTheme {
        Category(
            Modifier.height(100.dp),
            text = stringResource(id = R.string.category_train),
            color = listOf(Color.White, Color.Blue),
            painter = painterResource(id = R.drawable.ic_train)
        ) {}
    }
}

@Composable
@Preview
fun CategoryActivityPreview() {
    JetTripsTheme {
        Category(
            Modifier.height(100.dp),
            text = stringResource(id = R.string.category_activities),
            color = listOf(Color.White, Color.Cyan),
            painter = painterResource(id = R.drawable.ic_homestay)
        ) {}
    }
}
