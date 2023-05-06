package com.syrjakojyrjanai.reminderapp.ui.landing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.syrjakojyrjanai.reminderapp.R

@Composable
fun LandingScreen(
    modifier: Modifier,
    navigationController: NavController
) {

    Column (
        modifier = modifier.padding(20.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
    ) {

        Text(
            text = stringResource(R.string.app_name),
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
        )



        Button(
            onClick = {
                navigationController.navigate("login")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
            ,
            shape = RoundedCornerShape(corner = CornerSize(10.dp)),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(217,217,217,255)),
        ) {
            Text(stringResource(R.string.log_in))
        }

        Button(
            onClick = {
                navigationController.navigate("signup")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
            ,
            shape = RoundedCornerShape(corner = CornerSize(10.dp)),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(217,217,217,255)),
        ) {
            Text(stringResource(R.string.sign_up))
        }


    }
}