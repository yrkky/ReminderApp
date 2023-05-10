package com.syrjakoyrjanai.reminderapp.ui.reminder

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.syrjakoyrjanai.core.domain.entity.Category
import com.syrjakoyrjanai.core.domain.entity.Reminder
import com.syrjakoyrjanai.mobilecomp.ui.category.CategoryViewState
import java.lang.Math.abs
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun ReminderList(
    selectedCategory: Category,
    mainViewModel: MainViewModel,
    navigationController: NavController,
    selectedState: MutableState<String>,
    latitude: MutableState<Double>,
    longitude: MutableState<Double>,
    filterNearby: MutableState<Boolean>
) {
    if (selectedCategory.name == "All") {
        mainViewModel.loadAllReminders()
    } else {
        mainViewModel.loadRemindersFor(selectedCategory)
    }

    val reminderViewState by mainViewModel.reminderState.collectAsState()
    when (reminderViewState) {
        is ReminderViewState.Loading -> {}
        is ReminderViewState.Success -> {
            val reminderList = (reminderViewState as ReminderViewState.Success).data

            val passedReminders = reminderList.filter { it.reminderTime.isBefore(LocalDateTime.now()) }
            val upcomingReminders = reminderList.filter { it.reminderTime.isAfter(LocalDateTime.now()) }


            LazyColumn(
                contentPadding = PaddingValues(0.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                var selectedStateReminders = reminderList
                when (selectedState.value) {
                    "Passed" -> {selectedStateReminders = passedReminders}
                    "Upcoming" -> {selectedStateReminders = upcomingReminders}
                    "All" -> {selectedStateReminders = reminderList}
                }
                if (filterNearby.value) {
                    selectedStateReminders = filterNearbyReminders(selectedStateReminders, latitude.value, longitude.value)
                }
                items(selectedStateReminders) { item ->
                    //val reminder_calendartime = Calendar.getInstance()
                    //reminder_calendartime.set(item.reminderTime.year, item.reminderTime.monthValue-1, item.reminderTime.dayOfMonth, item.reminderTime.hour, item.reminderTime.minute)
                    //if (reminder_calendartime.timeInMillis <= System.currentTimeMillis()) {
                        ReminderListItem(
                            reminder = item,
                            category = categoryIdToName(mainViewModel, item.categoryId),
                            onClick = {},
                            navigationController = navigationController,
                            mainViewModel = mainViewModel,
                        )
                    //}
                }
            }
        }
    }

}

private fun filterNearbyReminders(reminders: List<Reminder>, latitude: Double, longitude: Double): List<Reminder> {
    val nearbyReminders = reminders.filter { reminder ->
        if (reminder.location_x == null || reminder.location_y == null) {
            return@filter false
        }

        val latitudeDifference = kotlin.math.abs(reminder.location_x - latitude)
        val longitudeDifference = kotlin.math.abs(reminder.location_y - longitude)

        // reminder that are inside ~1km
        latitudeDifference <= 0.009009 && longitudeDifference <= 0.009009
    }
    return nearbyReminders
}


@Composable
private fun ReminderListItem(
    reminder: Reminder,
    category: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    navigationController: NavController,
    mainViewModel: MainViewModel,
) {
    ConstraintLayout(
        modifier = modifier.clickable{
            mainViewModel.setEditReminder(reminder)
            navigationController.navigate("editreminder")
        }.fillMaxWidth()
    ) {
        val (dividerRef, titleRef, categoryRef, iconRef, dateRef) = createRefs()
        Divider(
            Modifier.constrainAs(dividerRef) {
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }
        )

        // title
        Icon(
            imageVector = nameToIcon(reminder.icon),
            contentDescription = null,
        )

        Text(
            text = reminder.title,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.constrainAs(titleRef) {
                linkTo(
                    start = parent.start,
                    end = iconRef.start,
                    startMargin = 24.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
                top.linkTo(parent.top, margin = 10.dp)
                width = Dimension.preferredWrapContent
            }
        )

        // category
        Text(
            text = category,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.constrainAs(categoryRef) {
                linkTo(
                    start = parent.start,
                    end = iconRef.start,
                    startMargin = 24.dp,
                    endMargin = 8.dp,
                    bias = 0f // float this towards the start. this was is the fix we needed
                )
                top.linkTo(titleRef.bottom, margin = 6.dp)
                bottom.linkTo(parent.bottom, 10.dp)
                width = Dimension.preferredWrapContent
            }
        )

        // date
        Text(
            text = LocalDateTime.parse(reminder.reminderTime.toString()).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm")),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.constrainAs(dateRef) {
                linkTo(
                    start = categoryRef.end,
                    end = iconRef.start,
                    startMargin = 8.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
                centerVerticallyTo(categoryRef)
                top.linkTo(titleRef.bottom, 6.dp)
                bottom.linkTo(parent.bottom, 10.dp)
            }
        )

        // icon
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)
                .constrainAs(iconRef) {
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(parent.end)
                }
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = ""
            )
        }
    }
}

private fun nameToIcon(name: String) : ImageVector {
    return when (name) {
        "immediate" -> {
            Icons.Outlined.Error
        }
        "urgent" -> {
            Icons.Outlined.Warning
        }
        "late" -> {
            Icons.Outlined.AssignmentLate
        }
        "important" -> {
            Icons.Outlined.LabelImportant
        }
        "incomplete" -> {
            Icons.Outlined.StarHalf
        }
        "new" -> {
            Icons.Outlined.StarOutline
        }
        else -> {
            Icons.Outlined.StarOutline
        }
    }
}

@Composable
private fun categoryIdToName(viewModel: MainViewModel, id: Long): String {
    val viewState by viewModel.categoryState.collectAsState()

    when (viewState) {
        is CategoryViewState.Success -> {
            val categories = (viewState as CategoryViewState.Success).data
            Log.i("categoryToName", "ID: ${id}")
            val filtered = categories.filter { it.categoryId.equals(id) }
            Log.i("categoryToName", "FOUND: ${filtered.first().name}")
            return filtered.first().name
        }
    }
    return "Uncategorized"
}