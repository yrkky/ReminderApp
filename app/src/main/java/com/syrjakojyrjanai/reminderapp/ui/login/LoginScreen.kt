package com.syrjakojyrjanai.reminderapp.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.syrjakojyrjanai.reminderapp.R
import com.syrjakojyrjanai.reminderapp.tools.hasEnoughLength
import com.syrjakojyrjanai.reminderapp.tools.isValidEmail

@Composable
fun LoginScreen(
    modifier: Modifier,
    navigationController: NavController
) {

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    fun buttonEnabled(): Boolean {
        return (email.value.isValidEmail()
                && password.value.hasEnoughLength(8)
                )
    }

    Column(
        modifier = modifier.padding(20.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
    ) {

        Text(
            text = stringResource(R.string.app_name),
            fontSize = 50.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.login_benefits),
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(300.dp)
        )

        Spacer(modifier = Modifier.height(107.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email.value,
            onValueChange = { emailString -> email.value = emailString },
            label = { Text(stringResource(R.string.email)) },
            shape = RoundedCornerShape(corner = CornerSize(10.dp))
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password.value,
            onValueChange = { passwordString -> password.value = passwordString },
            label = { Text(stringResource(R.string.password)) },
            shape = RoundedCornerShape(corner = CornerSize(10.dp)),
            visualTransformation = PasswordVisualTransformation(),
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
            Text(stringResource(R.string.login))
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
        ) {
            Text(stringResource(R.string.back))
        }

        Spacer(modifier = Modifier.height(125.dp))

    }

}