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
    val size: Dp = 48.dp,
    val isEnabled: Boolean = false
)

enum class CATEGORY_ID {
    CATEGORY_FLIGHT,
    CATEGORY_HOTEL,
    CATEGORY_CAB,
    CATEGORY_HOLIDAY
}

val categories = listOf(
    CategoryItem(
        categoryId = CATEGORY_ID.CATEGORY_FLIGHT,
        textResId = R.string.category_flight,
        colors = listOf(Color.White, Color.Green),
        iconResId = R.drawable.category_place,
        isEnabled = true
    ),
    CategoryItem(
        categoryId = CATEGORY_ID.CATEGORY_HOTEL,
        textResId = R.string.category_hotel,
        colors = listOf(Color.White, Color.Blue),
        iconResId = R.drawable.category_hotels
    ),
    CategoryItem(
        categoryId = CATEGORY_ID.CATEGORY_CAB,
        textResId = R.string.category_cab,
        colors = listOf(Color.White, Color.Yellow),
        iconResId = R.drawable.category_cab
    ),
    CategoryItem(
        categoryId = CATEGORY_ID.CATEGORY_HOLIDAY,
        textResId = R.string.category_holiday,
        colors = listOf(Color.White, Color.Red),
        iconResId = R.drawable.category_holiday
    )
)

