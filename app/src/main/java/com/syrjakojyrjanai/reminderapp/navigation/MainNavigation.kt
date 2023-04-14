package com.syrjakojyrjanai.reminderapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.syrjakojyrjanai.reminderapp.ui.home.Home


@Composable
fun MainNavigation (){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(route = "home") {
            Home(navController = navController)
        }
    }
}