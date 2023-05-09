package com.syrjakojyrjanai.reminderapp.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AddLocation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.insets.systemBarsPadding

@Composable
fun MainScreen(
    navigationController: NavController,
    modifier: Modifier,
){
    Scaffold (
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigationController.navigate(route = "Reminder") },
                contentColor = Color(255,255,255, 255),
                modifier = Modifier.padding(all = 20.dp).size(70.dp),
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp),
                    Color(0,0,0, 255)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        bottomBar = {
            BottomAppBar(
                backgroundColor = Color(217, 217, 217, 255),
                contentColor = Color(0, 0, 0, 255),
                modifier = Modifier.heightIn(80.dp),


            ) {
                Spacer(modifier = Modifier.weight(0.06f))

                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Home,
                        contentDescription = null,
                        Modifier.size(50.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(0.1f))

                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = null,
                        Modifier.size(50.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(0.5f))

                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Description,
                        contentDescription = null,
                        Modifier.size(50.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(0.1f))

                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = null,
                        Modifier.size(50.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(0.06f))
            }
        }
    ){

    }
}