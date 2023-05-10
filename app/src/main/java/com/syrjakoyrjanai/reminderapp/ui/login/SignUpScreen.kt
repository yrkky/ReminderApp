package com.syrjakoyrjanai.reminderapp.ui.login

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.syrjakoyrjanai.reminderapp.R
import com.syrjakoyrjanai.reminderapp.tools.hasEnoughLength
import com.syrjakoyrjanai.reminderapp.tools.isValidEmail
import java.io.Console

@Composable
fun SignUpScreen(
    modifier: Modifier,
    navigationController: NavController
) {

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    /**
     * Checks the email
     * @return True if need to raise error, otherwise False
     */
    fun emailError(): Boolean {
        return email.value.isNotEmpty() && !email.value.isValidEmail()
    }

    /**
     * Checks the passwords matching
     * @return True if need to raise error, otherwise False
     */
    fun passwordError(pass: String, check: String): Boolean {
        val validLength: Boolean = pass.isNotEmpty() && password.value.hasEnoughLength(8)
        val match: Boolean = pass.compareTo(check) == 0
        return validLength && !match
    }

    fun buttonEnabled(): Boolean {
        return (email.value.isValidEmail()
                && !passwordError(confirmPassword.value, password.value)
                && password.value.isNotEmpty()
                && confirmPassword.value.isNotEmpty()
                )
    }

    Column(
        modifier = modifier.padding(20.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {

        Text(
            text = stringResource(R.string.app_name),
            fontSize = 50.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.sign_up_benefits),
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(300.dp)
        )

        Spacer(modifier = Modifier.height(93.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email.value,
            onValueChange = { emailString -> email.value = emailString },
            label = { Text(stringResource(R.string.email)) },
            shape = RoundedCornerShape(corner = CornerSize(10.dp)),
            isError = emailError(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password.value,
            onValueChange = { passwordString -> password.value = passwordString },
            label = { Text(stringResource(R.string.password)) },
            shape = RoundedCornerShape(corner = CornerSize(10.dp)),
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError(password.value, password.value)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = confirmPassword.value,
            onValueChange = { confirmPasswordString ->
                confirmPassword.value = confirmPasswordString
            },
            label = { Text(stringResource(R.string.confirm_password)) },
            shape = RoundedCornerShape(corner = CornerSize(10.dp)),
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError(confirmPassword.value, password.value)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                // TODO: Connect to a real backend
                navigationController.navigate("home")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(corner = CornerSize(10.dp)),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(217, 217, 217, 255)),
            enabled = buttonEnabled()
        ) {
            Text(stringResource(R.string.create_new_account))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                navigationController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(corner = CornerSize(10.dp)),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(217, 217, 217, 255)),
            enabled = true
        ) {
            Text(stringResource(R.string.back))
        }

        Spacer(modifier = Modifier.height(125.dp))

    }


}