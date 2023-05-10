package com.syrjakoyrjanai.reminderapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.syrjakoyrjanai.reminderapp.ui.home.Home
import com.syrjakoyrjanai.reminderapp.ui.home.Notes
import com.syrjakoyrjanai.reminderapp.ui.home.Reminders
import com.syrjakoyrjanai.reminderapp.ui.home.Settings
import com.syrjakoyrjanai.reminderapp.ui.landing.LandingScreen
import com.syrjakoyrjanai.reminderapp.ui.login.LoginScreen
import com.syrjakoyrjanai.reminderapp.ui.login.SignUpScreen
import com.syrjakoyrjanai.reminderapp.ui.reminder.AddReminder

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
        composable(route = "settings") {
            Settings(navigationController = navigationController)
        }
        composable(route = "reminders") {
            Reminders(navigationController = navigationController)
        }
        composable(route = "notes") {
            Notes(navigationController = navigationController)
        }
        composable(route = "addreminder") {
            AddReminder(navigationController = navigationController)
        }
    }
}