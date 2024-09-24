package com.example.jettrips.ui.screens.bottombar

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jettrips.ui.navigation.ROUTE_ACCOUNT
import com.example.jettrips.ui.navigation.ROUTE_BOOKING
import com.example.jettrips.ui.navigation.ROUTE_FAVORITE
import kotlinx.serialization.Serializable

@Composable
fun HomeBottomBar(navController: NavController) {
    val screens = listOf(
        BottomBarItems.HOME,
        BottomBarItems.BOOKINGS,
        BottomBarItems.FAVORITES,
        BottomBarItems.ACCOUNT
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val shouldShowBottomBar = screens.any { it.route == currentDestination?.route }
    if (true)
        NavigationBar {
            screens.forEach {
                NavigationBarItem(
                    selected = currentDestination?.route == it.route,
                    onClick = {
                        navController.navigate(it.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                    icon = { Image(imageVector = it.icon, contentDescription = it.label) },
                    label = {
                        Text(
                            text = it.label
                        )
                    })
            }
        }

}

sealed class BottomBarItems(val label: String, val icon: ImageVector, val route: Any) {
    object HOME : BottomBarItems("Home", Icons.Filled.Home, ROUTE_HOME)
    object BOOKINGS : BottomBarItems("Bookings", Icons.Filled.Info, ROUTE_BOOKING)
    object FAVORITES : BottomBarItems("Favorites", Icons.Filled.Favorite, ROUTE_FAVORITE)
    object ACCOUNT : BottomBarItems("Account", Icons.Filled.Person, ROUTE_ACCOUNT)
    object SEARCH : BottomBarItems("Search", Icons.Filled.Favorite, ROUTE_HOME)
}


//sealed class BottomBarItems(val label: String, val icon: ImageVector, val route: String) {
//    object HOME : BottomBarItems("Home", Icons.Filled.Home, "sdds")
//    object BOOKINGS : BottomBarItems("Bookings", Icons.Filled.Info, "sdds")
//    object FAVORITES : BottomBarItems("Favorites", Icons.Filled.Favorite, "sdds")
//    object ACCOUNT : BottomBarItems("Account", Icons.Filled.Person, "sdds")
//    object SEARCH : BottomBarItems("Search", Icons.Filled.Favorite, "sdds")
//}

@Serializable
object ROUTE_HOME


@Composable
@Preview
fun HomeBottomBarPreview() {
//    HomeBottomBar({})
}
