package com.syrjakoyrjanai.reminderapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.syrjakoyrjanai.reminderapp.R
import java.util.Calendar
import java.util.Locale


@Composable
fun Calendar(
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
            CalendarView()
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
            offset = DpOffset((halfScreenWidth * 0.8f), (5).dp)
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

@Composable
private fun CalendarView(
    modifier: Modifier = Modifier,
    calendar: Calendar = Calendar.getInstance()
    
){
    val calendar = Calendar.getInstance()
    val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
    val year = calendar.get(Calendar.YEAR).toString()
    val days = calendar.get(Calendar.DAY_OF_MONTH)
    val firstDay = calendar.get(Calendar.DAY_OF_WEEK)
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    val currentMonth = calendar.get(Calendar.MONTH).toString()
    val currentYear = calendar.get(Calendar.YEAR).toString()
    

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(255, 255, 255, 255))
    ) {
        // Month
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "$month, $year",
                color = Color(0, 0, 0, 255),
                fontSize = 40.sp,
            )
        }

        // Days of the week
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Sun",
                color = Color(0, 0, 0, 255),
                fontSize = 20.sp,
            )
            Text(
                text = "Mon",
                color = Color(0, 0, 0, 255),
                fontSize = 20.sp,
            )
            Text(
                text = "Tue",
                color = Color(0, 0, 0, 255),
                fontSize = 20.sp,
            )
            Text(
                text = "Wed",
                color = Color(0, 0, 0, 255),
                fontSize = 20.sp,
            )
            Text(
                text = "Thu",
                color = Color(0, 0, 0, 255),
                fontSize = 20.sp,
            )
            Text(
                text = "Fri",
                color = Color(0, 0, 0, 255),
                fontSize = 20.sp,
            )
            Text(
                text = "Sat",
                color = Color(0, 0, 0, 255),
                fontSize = 20.sp,
            )
        }

        // Days of the month
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            for (i in 1 until firstDay) {
                Text(
                    text = " ",
                    color = Color(0, 0, 0, 255),
                    fontSize = 20.sp,
                )
            }
            for (i in 1..days) {
                if (i == currentDay && month == currentMonth && year == currentYear) {
                    Column(
                        modifier = Modifier
                            .background(Color(0, 0, 0, 255))
                            .width(50.dp)
                            .height(50.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "$i",
                            color = Color(255, 255, 255, 255),
                            fontSize = 20.sp,
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "$i",
                            color = Color(0, 0, 0, 255),
                            fontSize = 20.sp,
                        )
                    }
                }
            }
        }
    }
}