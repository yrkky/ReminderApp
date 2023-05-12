package com.syrjakoyrjanai.reminderapp.ui.reminder

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.systemBarsPadding
import com.syrjakoyrjanai.core.domain.entity.Category
import com.syrjakoyrjanai.core.domain.entity.Note
import com.syrjakoyrjanai.core.domain.entity.Reminder
import com.syrjakoyrjanai.reminderapp.R
import com.syrjakoyrjanai.reminderapp.ui.note.NoteViewModel
import com.syrjakoyrjanai.reminderapp.ui.note.NoteViewState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@Composable
fun AddReminder(
    navigationController: NavController,
    viewModel: MainViewModel = hiltViewModel(),
    noteViewModel: NoteViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val reminderTitle = remember { mutableStateOf("") }
    val reminderCategory = remember { mutableStateOf("") }
    val reminderTime = remember { mutableStateOf("") }
    val shouldNotify = remember { mutableStateOf(true) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {}
    )

    fun buttonEnabled(): Boolean {
        return (
                reminderTitle.value.isNotEmpty() &&
                reminderCategory.value.isNotEmpty() &&
                reminderTime.value.isNotEmpty()
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
                        Text(
                            text = stringResource(R.string.back),
                            color = Color.Black,
                            fontSize = 16.sp
                        )
                    }


                    Button(
                        onClick = {
                            if (buttonEnabled()) {
                                // Ask permissions to notify
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    requestPermission(
                                        context = context,
                                        permission = Manifest.permission.POST_NOTIFICATIONS,
                                        requestPermission = { launcher.launch(Manifest.permission.POST_NOTIFICATIONS) }
                                    )
                                }
                                // Save the reminder
                                viewModel.saveReminder(
                                    Reminder(
                                        title = reminderTitle.value,
                                        reminderTime = LocalDateTime.parse(reminderTime.value),
                                        creationTime = LocalDateTime.now(),
                                        categoryId = getCategoryId(viewModel, reminderCategory.value),
                                        reminderSeen = LocalDateTime.now(),
                                    ),
                                    notify = shouldNotify.value
                                )
                                navigationController.popBackStack()
                            } else {
                                // Some kind of alert why can't save
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
                    ) {
                        // Save
                        Text(
                            text = stringResource(R.string.save),
                            color = if (buttonEnabled()) Color.Black else Color(0,0,0,120),
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = null,
                            tint = if (buttonEnabled()) Color.Black else Color(0,0,0,120),
                        )
                    }
                }
            }

            // Actual content

            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(16.dp)
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset((-16).dp),
                    value = reminderTitle.value,
                    onValueChange = { reminderTitle_ -> reminderTitle.value = reminderTitle_ },
                    placeholder = {
                        Text(
                        text = stringResource(R.string.reminder_title),
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
                    text = stringResource(R.string.reminder_date),
                    color = Color.Black,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(5.dp))
                DatePicker(
                    remindtime = reminderTime,
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(R.string.reminder_category),
                    color = Color.Black,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(5.dp))

                CategoryListDropdown(
                    viewModel = viewModel,
                    category = reminderCategory
                )

                Spacer(modifier = Modifier.height(100.dp))

                NotesRow(
                    noteViewModel = noteViewModel,
                    navigationController = navigationController
                )

                Spacer(modifier = Modifier.fillMaxHeight(0.7f))

                Button(
                    onClick = {
                        // Ask permissions to notify
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            requestPermission(
                                context = context,
                                permission = Manifest.permission.POST_NOTIFICATIONS,
                                requestPermission = { launcher.launch(Manifest.permission.POST_NOTIFICATIONS) }
                            )
                        }
                        //Save reminder
                        viewModel.saveReminder(
                            Reminder(
                                title = reminderTitle.value,
                                reminderTime = LocalDateTime.parse(reminderTime.value),
                                creationTime = LocalDateTime.now(),
                                categoryId = getCategoryId(viewModel, reminderCategory.value),
                                reminderSeen = LocalDateTime.now(),
                            ),
                            notify = shouldNotify.value
                        )
                        navigationController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(corner = CornerSize(10.dp)),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(217, 217, 217, 255)),
                    enabled = buttonEnabled()
                ) {
                    Text(stringResource(R.string.save))
                }


            }

        }
    }

}

@Composable
private fun NotesRow(
    noteViewModel: NoteViewModel,
    navigationController: NavController
) {
    val noteViewState by noteViewModel.noteState.collectAsState()

    noteViewModel.loadAllNotes()

    when (noteViewState) {
        is NoteViewState.Loading -> {}
        is NoteViewState.Success -> {
            val noteList = (noteViewState as NoteViewState.Success).data

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.50f)
            ) {
                items(noteList) { item ->
                    NoteCardsItem(
                        note = item,
                        navigationController = navigationController,
                        modifier = Modifier,
                        onClick = {/*TODO*/ },
                        noteViewModel = noteViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun NoteCardsItem(
    note: Note,
    navigationController: NavController,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    noteViewModel: NoteViewModel
) {
    val configuration = LocalConfiguration.current
    val cardWith = (configuration.screenWidthDp.dp) * 0.3f * 1.4f
    val cardHeight= (configuration.screenHeightDp.dp) * 0.14f * 1.4f

    Card(
        modifier = modifier
            .padding(10.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp,
        backgroundColor = Color(217,217,217, 255)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .width(cardWith)
                .height(cardHeight),
        ) {
            val (title, content, selectButton) = createRefs()

            Text(
                text = note.title,
                maxLines = 2,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(parent.top, margin = 10.dp)
                        start.linkTo(parent.start, margin = 10.dp)
                        end.linkTo(parent.end, margin = 10.dp)
                    }
                    .padding(10.dp)
            )

            var selected = remember { mutableStateOf(false) }

            IconButton(
                onClick = {
                        selected.value = !selected.value   
                },
                enabled = true,
                modifier = Modifier
                    .constrainAs(selectButton) {
                        top.linkTo(parent.top, margin = 0.dp)
                        end.linkTo(parent.end, margin = 0.dp)
                    }
                    .padding(5.dp)
            ) {
                Icon(
                    imageVector = if (selected.value) Icons.Filled.RadioButtonChecked else Icons.Filled.RadioButtonUnchecked,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
            
            Text(
                text = note.message,
                maxLines = 2,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(content) {
                        top.linkTo(title.bottom, margin = 10.dp)
                        start.linkTo(parent.start, margin = 10.dp)
                        end.linkTo(parent.end, margin = 10.dp)
                    }
                    .padding(10.dp)
            )
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


private fun getCategoryId(viewModel: MainViewModel, categoryName: String): Long {
    return viewModel.categories.value.first { it.name.lowercase() == categoryName.lowercase() }.categoryId
}

private fun getCategory(viewModel: MainViewModel, categoryId: Long): Category {
    return viewModel.categories.value.first { it.categoryId == categoryId }
}


@Composable
private fun CategoryListDropdown(
    viewModel: MainViewModel,
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


@Composable
private fun DatePicker(
    viewModel: MainViewModel = hiltViewModel(),
    remindtime: MutableState<String>
) {
    var localContext = LocalContext.current
    var mDate = remember { mutableStateOf("") }
    var mTime = remember { mutableStateOf("") }
    var mDatetime = remember { mutableStateOf("") }

    var mYear: Int
    var mMonth: Int
    var mDay: Int
    var mHour: Int
    var mMinute: Int
    var mCalendar = Calendar.getInstance()

    mYear = mCalendar[Calendar.YEAR]
    mMonth = mCalendar[Calendar.MONTH]
    mDay = mCalendar[Calendar.DAY_OF_MONTH]
    mHour = mCalendar[Calendar.HOUR_OF_DAY]
    mMinute = mCalendar[Calendar.MINUTE]
    mCalendar.time = Date()

    var mDatePickerDialog = DatePickerDialog(
        localContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth.${mMonth + 1}.$mYear"
        }, mYear, mMonth, mDay
    )

    val mTimePickerDialog = TimePickerDialog(
        localContext,
        { _, mHour: Int, mMinute: Int ->
            mTime.value = "$mHour.$mMinute"
        }, mHour, mMinute, true
    )

    mDatetime.value = mDate.value + " " + mTime.value
    // LocalDateTime.of(mYear, mMonth, mDay, mHour, mMinute).toString()

    if (mDate.value.isNotEmpty() && mTime.value.isNotEmpty()) {
        remindtime.value = LocalDateTime.of(
            mDate.value.split(".")[2].toInt(),
            mDate.value.split(".")[1].toInt(),
            mDate.value.split(".")[0].toInt(),
            mTime.value.split(".")[0].toInt(),
            mTime.value.split(".")[1].toInt(),
        ).toString()

        mDatetime.value = LocalDateTime
            .parse(remindtime.value)
            .format(
                DateTimeFormatter
                    .ofPattern("dd.MM.yyyy HH.mm")
            )
    }

    Column(modifier = Modifier.fillMaxWidth()
    ) {

        OutlinedTextField(
            value = mDatetime.value.ifBlank { stringResource(R.string.reminder_choose_date) },
            modifier = Modifier.fillMaxWidth(),
            // label = { Text(text = stringResource(R.string.reminder_date)) },
            onValueChange = { mDatetime.value = it},
            readOnly = true,
            placeholder = { Text(text = stringResource(R.string.reminder_choose_date)) },
            trailingIcon = {
                IconButton(onClick = {
                    mTimePickerDialog.show()
                    mDatePickerDialog.show()
                })
                {
                    Icon(
                        imageVector = Icons.Filled.EditCalendar,
                        contentDescription = null,
                        tint = Color(0, 0, 0, 255)
                    )
                }
            },
        )

    }
}

private fun requestPermission(
    context: Context,
    permission: String,
    requestPermission: () -> Unit
) {
    if (ContextCompat.checkSelfPermission(
            context,
            permission
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        requestPermission()
    }
}






