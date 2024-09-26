package com.example.jettrips.ui.screens.home

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
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
                    fontFamily = sanComicsFontFamily,
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
                .verticalScroll(rememberScrollState()) // Adds scrolling
        ) {
            Row(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                categories.take(2).forEach { category ->
                    Category(
                        modifier = Modifier.weight(1f),
                        text = stringResource(id = category.textResId),
                        color = category.colors,
                        painter = painterResource(id = category.iconResId),
                        size = category.size,
                        onCategoryClicked = { onCategoryClicked(category.categoryId) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                categories.drop(2).forEach { category ->
                    Category(
                        modifier = Modifier.weight(1f),
                        text = stringResource(id = category.textResId),
                        color = category.colors,
                        painter = painterResource(id = category.iconResId),
                        size = category.size,
                        onCategoryClicked = { onCategoryClicked(category.categoryId) }
                    )
                }
            }
            ExploreCard()
            TravelBlogCard()
        }
    }
}


@Composable
fun Category(
    modifier: Modifier,
    text: String = "Hotel",
    color: List<Color>,
    painter: Painter,
    size: Dp = 64.dp,
    onCategoryClicked: () -> Unit
) {

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
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = color,
                        start = Offset(0f, 0f),
                        end = Offset(
                            1000f,
                            1000f
                        ) // Adjust end offset for more or less diagonal effect
                    )
                )
                .fillMaxSize()
        ) {
            Text(
                text = text,
                color = Color.Black,
                fontFamily = sanComicsFontFamily,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )
            Image(
                painter = painter,
                contentDescription = "Category image",
                modifier = Modifier
                    .size(size)
                    .align(Alignment.BottomEnd)
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
            Modifier.height(200.dp),
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
            Modifier.height(200.dp),
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
            Modifier.height(200.dp),
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
            Modifier.height(200.dp),
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
            Modifier.height(200.dp),
            text = stringResource(id = R.string.category_activities),
            color = listOf(Color.White, Color.Cyan),
            painter = painterResource(id = R.drawable.ic_homestay)
        ) {}
    }
}
