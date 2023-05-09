package com.syrjakojyrjanai.reminderapp.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun SignUpScreen(
    modifier: Modifier,
    navigationController: NavController
) {

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    fun buttonEnabled(): Boolean {
        return password.value.length >= 8 && confirmPassword.value == password.value
    }




}