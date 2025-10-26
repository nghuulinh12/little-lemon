package com.example.littlelemon

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MyNavigation(
    navController: NavHostController,
    isUserOnboarded: Boolean
) {

    val startDestination = if (isUserOnboarded) {
        Home.route
    } else {
        Onboarding.route
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Onboarding.route) {
            OnboardingScreen(
                navController = navController
            )
        }
        composable(Profile.route) {
            ProfileScreen(navController = navController)
        }
    }

}