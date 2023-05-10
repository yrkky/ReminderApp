package com.syrjakoyrjanai.reminderapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.syrjakoyrjanai.reminderapp.ui.home.Home
import com.syrjakoyrjanai.reminderapp.ui.landing.LandingScreen
import com.syrjakoyrjanai.reminderapp.ui.login.LoginScreen
import com.syrjakoyrjanai.reminderapp.ui.login.SignUpScreen

@Composable
fun MainNavigation (){
    val navigationController = rememberNavController()
    NavHost(
        navController = navigationController,
        startDestination = "landing"
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
            Home(navigationController = navigationController)
        }
    }
}