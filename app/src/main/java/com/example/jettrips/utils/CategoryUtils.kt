package com.example.jettrips.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jettrips.R

data class CategoryItem(
    val categoryId: CATEGORY_ID,
    val textResId: Int,
    val colors: List<Color>,
    val iconResId: Int,
    val size: Dp = 64.dp
)

enum class CATEGORY_ID {
    CATEGORY_FLIGHT,
    CATEGORY_HOTEL,
    CATEGORY_CAB,
    CATEGORY_TRAIN,
    CATEGORY_ACTIVITY
}

val categories = listOf(
    CategoryItem(
        categoryId = CATEGORY_ID.CATEGORY_HOTEL,
        textResId = R.string.category_hotel,
        colors = listOf(Color.White, Color.Blue),
        iconResId = R.drawable.ic_homestay,
        size = 96.dp
    ),
    CategoryItem(
        categoryId = CATEGORY_ID.CATEGORY_FLIGHT,
        textResId = R.string.category_flight,
        colors = listOf(Color.White, Color.Green),
        iconResId = R.drawable.ic_flights,
        size = 96.dp
    ),
    CategoryItem(
        categoryId = CATEGORY_ID.CATEGORY_CAB,
        textResId = R.string.category_cab,
        colors = listOf(Color.White, Color.Yellow),
        iconResId = R.drawable.ic_car
    ),
    CategoryItem(
        categoryId = CATEGORY_ID.CATEGORY_TRAIN,
        textResId = R.string.category_train,
        colors = listOf(Color.White, Color.Red),
        iconResId = R.drawable.ic_train
    ),
    CategoryItem(
        categoryId = CATEGORY_ID.CATEGORY_ACTIVITY,
        textResId = R.string.category_activities,
        colors = listOf(Color.White, Color.Cyan),
        iconResId = R.drawable.ic_activity
    )
)

