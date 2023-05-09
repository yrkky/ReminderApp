package com.syrjakojyrjanai.reminderapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.syrjakojyrjanai.reminderapp.ui.main.MainScreen
import com.syrjakojyrjanai.reminderapp.ui.landing.LandingScreen
import com.syrjakojyrjanai.reminderapp.ui.login.LoginScreen
import com.syrjakojyrjanai.reminderapp.ui.login.SignUpScreen

@Composable
fun MainNavigation (){
    val navigationController = rememberNavController()
    NavHost(
        navController = navigationController,
        startDestination = "home"
    ) {
        composable(route = "landing") {
            LandingScreen(navigationController = navigationController, modifier = Modifier.fillMaxSize())
        }
        composable(route = "login") {
            LoginScreen(navigationController = navigationController, modifier = Modifier.fillMaxSize())
        }
        composable(route = "signup") {
            SignUpScreen(navigationController = navigationController, modifier = Modifier.fillMaxSize())
        }
        composable(route = "home") {
            MainScreen(navigationController = navigationController, modifier = Modifier.fillMaxSize())
        }
    }
}