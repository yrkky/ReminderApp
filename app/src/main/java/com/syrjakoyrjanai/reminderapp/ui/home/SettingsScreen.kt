package com.syrjakoyrjanai.reminderapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Help
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material.icons.outlined.SentimentSatisfied
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.syrjakoyrjanai.reminderapp.R


@Composable
fun Settings(
    navigationController: NavController,
) {
    Scaffold (

        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Button(
                        onClick = { navigationController.popBackStack() },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(
                                255,
                                255,
                                255,
                                255
                            )
                        ),
                        elevation = null,
                        modifier = Modifier
                            .width(150.dp)
                            .height(125.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        // Back
                        Text(
                            text = stringResource(R.string.back),
                            color = Color.Black,
                            fontSize = 16.sp
                        )
                    }
                }


            }
        },

        bottomBar = {
            BottomBar(
                navigationController = navigationController
            )

        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(255, 255, 255, 255))
        ) {
            Row(
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = stringResource(R.string.settings),
                    color = Color(0, 0, 0, 255),
                    fontSize = 40.sp,
                )
            }
            SettingsChoices(
                modifier = Modifier
            )
        }
    }
}

// list of setting choices
// 1. users
// 2. Notifications
// 3. personalization
// 4. privacy
// 5. help
// 6. about
// there is a icon in front of each choice

@Composable
fun SettingsChoices(
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color(255, 255, 255, 255))
                .padding(10.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.ManageAccounts,
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .offset(10.dp, 10.dp)
            )
            Text(
                text = stringResource(R.string.users),
                color = Color(0, 0, 0, 255),
                fontSize = 20.sp,
                modifier = Modifier
                    .offset(20.dp, 10.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color(255, 255, 255, 255))
                .padding(10.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .offset(10.dp, 10.dp)
            )
            Text(
                text = stringResource(R.string.notifications),
                color = Color(0, 0, 0, 255),
                fontSize = 20.sp,
                modifier = Modifier
                    .offset(20.dp, 10.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color(255, 255, 255, 255))
                .padding(10.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Group,
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .offset(10.dp, 10.dp)
            )
            Text(
                text = stringResource(R.string.personalization),
                color = Color(0, 0, 0, 255),
                fontSize = 20.sp,
                modifier = Modifier
                    .offset(20.dp, 10.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color(255, 255, 255, 255))
                .padding(10.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.PrivacyTip,
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .offset(10.dp, 10.dp)
            )
            Text(
                text = stringResource(R.string.privacy),
                color = Color(0, 0, 0, 255),
                fontSize = 20.sp,
                modifier = Modifier
                    .offset(20.dp, 10.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color(255, 255, 255, 255))
                .padding(10.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Help,
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .offset(10.dp, 10.dp)
            )
            Text(
                text = stringResource(R.string.help),
                color = Color(0, 0, 0, 255),
                fontSize = 20.sp,
                modifier = Modifier
                    .offset(20.dp, 10.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color(255, 255, 255, 255))
                .padding(10.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.SentimentSatisfied,
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .offset(10.dp, 10.dp)
            )
            Text(
                text = stringResource(R.string.about),
                color = Color(0, 0, 0, 255),
                fontSize = 20.sp,
                modifier = Modifier
                    .offset(20.dp, 10.dp)
            )
        }
    }
}

@Composable
private fun BottomBar(
    navigationController: NavController
) {
    BottomAppBar(
        backgroundColor = Color(217, 217, 217, 255),
        contentColor = Color(0, 0, 0, 255),
        modifier = Modifier.heightIn(90.dp),


        ) {
        Spacer(modifier = Modifier.weight(0.06f))

        IconButton(
            onClick = {
                navigationController.navigate("home")
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.Home,
                contentDescription = null,
                Modifier.size(50.dp),
            )
        }

        Spacer(modifier = Modifier.weight(0.1f))

        IconButton(
            onClick = { navigationController.navigate("reminders") }
        ) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = null,
                Modifier.size(50.dp)
            )
        }

        Spacer(modifier = Modifier.weight(0.5f))

        IconButton(
            onClick = { navigationController.navigate("notes") }
        ) {
            Icon(
                imageVector = Icons.Outlined.Description,
                contentDescription = null,
                Modifier.size(50.dp)
            )
        }

        Spacer(modifier = Modifier.weight(0.1f))

        IconButton(
            onClick = { navigationController.navigate("settings") }
        ) {
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = null,
                Modifier.size(50.dp),
                tint = Color(255, 0, 0, 255)
            )
        }
    }
    popUpMenuButton(
        modifier = Modifier,
        navigationController = navigationController
    )
}

@Composable
private fun popUpMenuButton(
    modifier: Modifier = Modifier,
    navigationController: NavController
) {
    var expanded by remember { mutableStateOf(false) }

    val configuration = LocalConfiguration.current
    val halfScreenWidth = (configuration.screenWidthDp.dp)/2 - 40.dp

    FloatingActionButton(
        onClick = { },
        modifier = Modifier
            .size(90.dp)
            .offset((halfScreenWidth), (-40).dp),
        backgroundColor = Color(217,217,217, 255),
        elevation = FloatingActionButtonDefaults.elevation(0.dp)
    ) {
    }

    FloatingActionButton(
        contentColor = Color(217,217,217, 255),
        onClick = { expanded = true },
        modifier = Modifier
            .padding(10.dp)
            .size(70.dp)
            .offset((halfScreenWidth), (-40).dp),
        backgroundColor = Color(255,255,255, 255)
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            Color(0,0,0, 255)
        )
    }
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        DropdownMenu(
            modifier = modifier,
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(120.dp, (5).dp)
        ) {
            DropdownMenuItem(onClick = { navigationController.navigate("addreminder") }) {
                Text(stringResource(R.string.add_reminder))
            }
            DropdownMenuItem(onClick = { navigationController.navigate("addnotes") }) {
                Text(stringResource(R.string.add_note))
            }
        }
    }
}