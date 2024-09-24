package com.example.jettrips.ui.navigation

import PaymentDetailPage
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jettrips.ui.screens.account.MyAccount
import com.example.jettrips.ui.screens.bookings.MyBookings
import com.example.jettrips.ui.screens.favorites.Favorites
import com.example.jettrips.ui.screens.flight.FlightHome
import com.example.jettrips.ui.screens.home.HomeScreen
import com.example.jettrips.ui.screens.hotels.HotelsHome
import com.example.jettrips.utils.CATEGORY_ID
import kotlinx.serialization.Serializable

@Composable
fun ComposeNavGraph(
    modifier: Modifier,
    navController: NavHostController = rememberNavController()
) {
    //ROUTE_AUTH_NESTED
    NavHost(navController = navController, startDestination = ROUTE_HOME) {
        onboardingRoute(modifier, navController)
        composable<ROUTE_HOME> {
            HomeScreen(modifier = modifier, {
                when (it) {
                    CATEGORY_ID.CATEGORY_FLIGHT -> navController.navigate(route = ROUTE_FLIGHT)
                    else -> {
                        //DO nothing for now
                    }
                }
            }, navController = navController)
        }
        composable<ROUTE_BOOKING> {
            MyBookings(modifier = modifier)
        }
        composable<ROUTE_ACCOUNT> {
            MyAccount(modifier = modifier)
        }
        composable<ROUTE_FAVORITE> {
            Favorites(modifier = modifier)
        }
        composable<ROUTE_FLIGHT> {
            FlightHome(modifier = modifier) {
                navController.navigate(route = ROUTE_PAYMENT)
            }
        }
        composable<ROUTE_HOTEL> {
            HotelsHome(modifier = modifier) {
                navController.navigate(route = ROUTE_PAYMENT)
            }
        }
        composable<ROUTE_PAYMENT> {
            PaymentDetailPage(modifier = modifier) {
                navController.navigate(route = ROUTE_HOME) {
                    popUpTo(ROUTE_HOME)
                }
            }
        }
    }
}

// Routes
@Serializable
object ROUTE_HOME

@Serializable
object ROUTE_BOOKING

@Serializable
object ROUTE_SEARCH

@Serializable
object ROUTE_FAVORITE

@Serializable
object ROUTE_ACCOUNT

@Serializable
object ROUTE_FLIGHT

@Serializable
object ROUTE_HOTEL

@Serializable
object ROUTE_PAYMENT