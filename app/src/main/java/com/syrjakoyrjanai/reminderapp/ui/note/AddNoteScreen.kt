package com.syrjakoyrjanai.reminderapp.ui.note

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.syrjakoyrjanai.core.domain.entity.Category
import com.syrjakoyrjanai.core.domain.entity.Note
import com.syrjakoyrjanai.reminderapp.R
import com.syrjakoyrjanai.reminderapp.ui.reminder.MainViewModel
import java.time.LocalDateTime
import androidx.compose.ui.text.TextStyle
import com.google.accompanist.insets.systemBarsPadding

@Composable
fun AddNote(
    navigationController: NavController,
    viewModel: NoteViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val noteTitle = remember { mutableStateOf("") }
    val noteMessage = remember { mutableStateOf("") }
    val noteCategory = remember { mutableStateOf("") }

    fun buttonEnabled(): Boolean {
        return (
                noteTitle.value.isNotEmpty() &&
                noteCategory.value.isNotEmpty()
                )
    }

    Surface{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            TopAppBar(
                backgroundColor = Color.White,
            ){
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
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
                            contentDescription = "Back",
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        // Back
                         Text(
                            text = stringResource(R.string.back),
                            color = Color.Black,
                            fontSize = 16.sp
                        )
                    }

                    Button(
                        onClick = {
                            if (buttonEnabled()) {
                                    viewModel.addNote(
                                        Note(
                                            title = noteTitle.value,
                                            message = noteMessage.value,
                                            creationTime = LocalDateTime.now(),
                                            categoryId = getCategoryId(viewModel, noteCategory.value)
                                        ),
                                    )
                                    navigationController.popBackStack()
                            } else {
                                toastError(context)
                            }
                        },
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
                    )  {
                        // Save
                        Text(
                            text = stringResource(R.string.save),
                            color = if (buttonEnabled()) Color.Black else Color(0,0,0,120),
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = "Save",
                            tint = if (buttonEnabled()) Color.Black else Color(0,0,0,120),
                        )
                    }
                }
            }

            // Actual content

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(16.dp)
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset((-16).dp),
                    value = noteTitle.value,
                    onValueChange = { noteTitle_ -> noteTitle.value = noteTitle_ },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.note_title),
                            fontSize = 32.sp,
                        )
                    },
                    shape = RoundedCornerShape(corner = CornerSize(10.dp)),
                    textStyle = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        disabledTextColor = Color.Transparent,
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        focusedLabelColor = Color.Transparent,
                    ),
                    singleLine = true,
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(R.string.note_category),
                    color = Color.Black,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(5.dp))
                CategoryListDropdown(
                    viewModel = viewModel,
                    category = noteCategory
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(R.string.note_message),
                    color = Color.Black,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(5.dp))

                TextField(
                    value = noteMessage.value,
                    onValueChange = { noteMessage.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.Black,
                        unfocusedIndicatorColor = Color.Black,
                        disabledIndicatorColor = Color.Black,
                    ),
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp
                    ),
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        viewModel.addNote(
                            Note(
                                title = noteTitle.value,
                                message = noteMessage.value,
                                creationTime = LocalDateTime.now(),
                                categoryId = getCategoryId(viewModel, noteCategory.value)
                            ),
                        )
                        navigationController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(
                            217,
                            217,
                            217,
                            255
                        )
                    ),
                    enabled = buttonEnabled()
                ) {
                    Text(
                        text = stringResource(R.string.save),
                    )
                }
            }
        }
    }

}

fun toastError(context: Context) {
    Toast.makeText(
        context,
        context.getString(R.string.fill_error),
        Toast.LENGTH_LONG
    ).show()
}


private fun getCategoryId(viewModel: NoteViewModel, categoryName: String): Long {
    return viewModel.categories.value.first { it.name.lowercase() == categoryName.lowercase() }.categoryId
}


@Composable
private fun CategoryListDropdown(
    viewModel: NoteViewModel,
    category: MutableState<String>
) {
    val categoryState = viewModel.categories.collectAsState()
    var categoryState_withoutAll = categoryState.value.toMutableSet()
    Log.i("CategoryListDropdown", "categorylist: ${categoryState}")
    categoryState_withoutAll.remove(Category(categoryId=1, name="All"))
    Log.i("CategoryListDropdown", "categorylist_withoutAll: ${categoryState_withoutAll}")


    var expanded by remember { mutableStateOf(false) }
    val icon = if (expanded) {
        Icons.Outlined.ArrowDropUp // requires androidx.compose.material:material-icons-extended dependency
    } else {
        Icons.Filled.ArrowDropDown
    }

    Column {
        OutlinedTextField(
            value = category.value,
            onValueChange = { category.value = it},
            modifier = Modifier.fillMaxWidth(),
            // label = { Text(text = stringResource(R.string.reminder_choose_category)) },
            placeholder = {
                Text(
                    text = stringResource(R.string.reminder_choose_category),
                    color = Color.Black,
                )
            },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            categoryState_withoutAll.forEach { dropDownOption ->
                DropdownMenuItem(
                    onClick = {
                        category.value = dropDownOption.name
                        expanded = false
                    }
                ) {
                    Text(
                        text = dropDownOption.name,
                        color = Color.Black,
                    )
                }
            }
        }
    }
}