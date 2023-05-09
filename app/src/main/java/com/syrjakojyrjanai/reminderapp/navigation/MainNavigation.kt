package com.syrjakojyrjanai.reminderapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.syrjakojyrjanai.reminderapp.ui.landing.LandingScreen
import com.syrjakojyrjanai.reminderapp.ui.main.MainScreen


@Composable
fun MainNavigation (){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(route = "landing") {
            LandingScreen(navigationController = navController, modifier = Modifier.fillMaxSize())
        }
        composable(route = "home") {
            MainScreen(navigationController = navController, modifier = Modifier.fillMaxSize())
        }
    }
}