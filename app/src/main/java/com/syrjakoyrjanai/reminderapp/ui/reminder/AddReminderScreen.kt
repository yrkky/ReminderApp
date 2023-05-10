package com.syrjakoyrjanai.reminderapp.ui.reminder

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.systemBarsPadding
import com.syrjakoyrjanai.reminderapp.R


@Composable
fun AddReminder(
    navigationController: NavController,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val reminderTitle = remember { mutableStateOf("") }
    val reminderMessage = remember { mutableStateOf("") }
    val reminderCategory = remember { mutableStateOf("") }
    val reminderTime = remember { mutableStateOf("") }
    val shouldNotify = remember { mutableStateOf(true) }

    fun buttonEnabled(): Boolean {
        return (
                reminderTitle.value.isNotEmpty() &&
                reminderCategory.value.isNotEmpty() &&
                reminderTime.value.isNotEmpty() &&
                reminderMessage.value.isNotEmpty()
                )
    }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
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
                        Text(stringResource(R.string.back))
                    }


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
                        // Save
                        Text(
                            text = stringResource(R.string.save),
                            color = Color.Black,
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = null,
                            tint = Color.Black,
                        )
                    }
                }
            }
        }
    }

}
