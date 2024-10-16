package com.example.jettrips.ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.jettrips.ui.screens.onboarding.LoginPhoneScreen
import com.example.jettrips.ui.screens.onboarding.LoginScreen
import com.example.jettrips.ui.screens.onboarding.OnboardingScreen
import com.example.jettrips.ui.screens.onboarding.OtpScreen
import com.example.jettrips.ui.screens.onboarding.WelcomeScreen
import kotlinx.serialization.Serializable

@Serializable
object ROUTE_AUTH_NESTED

fun NavGraphBuilder.onboardingRoute(modifier: Modifier, navController: NavHostController) {
    navigation<ROUTE_AUTH_NESTED>(startDestination = ROUTE_AUTH_NESTED_WELCOME) {
        composable<ROUTE_AUTH_NESTED_WELCOME> {
            WelcomeScreen(modifier = Modifier, {
                navController.navigate(route = ROUTE_AUTH_NESTED_LOGIN_MOBILE)
            }, {
                navController.navigate(route = ROUTE_AUTH_NESTED_ONBOARDING)
            })
        }
        composable<ROUTE_AUTH_NESTED_ONBOARDING> {
            OnboardingScreen(modifier = modifier) {
                navController.navigate(route = ROUTE_AUTH_NESTED_LOGIN)
            }
        }
        composable<ROUTE_AUTH_NESTED_LOGIN_MOBILE> {
            LoginPhoneScreen(
                modifier = modifier,
                {
                    //On Continue button clicked
                    navController.navigate(route = ROUTE_AUTH_NESTED_OTP(phoneNumber = it))
                },
                {
                    //On Back button clicked
                    navController.popBackStack()
                })
        }
        composable<ROUTE_AUTH_NESTED_LOGIN> {
            LoginScreen(modifier = modifier) {
                navController.navigate(route = ROUTE_AUTH_NESTED_OTP)
            }
        }
        composable<ROUTE_AUTH_NESTED_OTP> {
            val phone = it.toRoute<ROUTE_AUTH_NESTED_OTP>()
            OtpScreen(modifier = modifier, phone.phoneNumber) {
                navController.navigate(route = ROUTE_HOME) {
                    popUpTo(ROUTE_AUTH_NESTED_ONBOARDING) {
                        inclusive = true
                    }
                }
            }
        }
    }
}


@Serializable
object ROUTE_AUTH_NESTED_WELCOME

@Serializable
object ROUTE_AUTH_NESTED_ONBOARDING

@Serializable
object ROUTE_AUTH_NESTED_LOGIN

@Serializable
object ROUTE_AUTH_NESTED_LOGIN_MOBILE

@Serializable
data class ROUTE_AUTH_NESTED_OTP(val phoneNumber: String)