package com.syrjakoyrjanai.reminderapp.ui.landing

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.syrjakoyrjanai.reminderapp.R

@Composable
fun LandingScreen(
    modifier: Modifier,
    navigationController: NavController
) {

    Column(
        modifier = modifier.padding(20.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
    ) {

        Text(
            text = stringResource(R.string.app_name),
            fontSize = 50.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(243.dp))


        Button(
            onClick = {
                navigationController.navigate("login")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(corner = CornerSize(10.dp)),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(217, 217, 217, 255)),
        ) {
            Text(stringResource(R.string.login))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                navigationController.navigate("signup")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(corner = CornerSize(10.dp)),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(217, 217, 217, 255)),
        ) {
            Text(stringResource(R.string.sign_up))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .height(50.dp)
                .align(CenterHorizontally),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally,

            ) {
            SignInButton(
                text = "Log In with Google",
                icon = painterResource(id = R.drawable.ic_google_logo),
                onClick = { navigationController.navigate("home") }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.skip),
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clickable { navigationController.navigate("home") }
                .align(CenterHorizontally)
        )
    }
}

@Composable
fun SignInButton(
    text: String,
    loadingText: String = "Signing in...",
    icon: Painter,
    isLoading: Boolean = false,
    shape: Shape = RoundedCornerShape(4.dp),
    borderColor: Color = Color.LightGray,
    backgroundColor: Color = MaterialTheme.colors.surface,
    progressIndicatorColor: Color = MaterialTheme.colors.primary,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier.clickable(
            enabled = !isLoading,
            onClick = onClick
        ),
        shape = shape,
        border = BorderStroke(width = 1.dp, color = borderColor),
        color = backgroundColor,
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = icon,
                contentDescription = "SignInButton",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(text = if (isLoading) loadingText else text)
            if (isLoading) {
                Spacer(modifier = Modifier.width(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(16.dp)
                        .width(16.dp),
                    strokeWidth = 2.dp,
                    color = progressIndicatorColor
                )
            }
        }
    }
}
