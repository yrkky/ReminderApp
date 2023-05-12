package com.syrjakoyrjanai.reminderapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.syrjakoyrjanai.core.domain.entity.Category
import com.syrjakoyrjanai.core.domain.entity.Reminder
import com.syrjakoyrjanai.reminderapp.R
import com.syrjakoyrjanai.reminderapp.ui.category.CategoryViewModel
import com.syrjakoyrjanai.reminderapp.ui.category.CategoryViewState
import com.syrjakoyrjanai.reminderapp.ui.note.NoteViewModel
import com.syrjakoyrjanai.reminderapp.ui.reminder.MainViewModel
import com.syrjakoyrjanai.reminderapp.ui.reminder.ReminderViewState
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Composable
fun Reminders(
    navigationController: NavController,
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    reminderViewModel: MainViewModel = hiltViewModel(),
) {

    val viewState by categoryViewModel.uiState.collectAsState()

    when (viewState) {
        is CategoryViewState.Success -> {
            val selectedCategory = (viewState as CategoryViewState.Success).selectedCategory
            val categories = (viewState as CategoryViewState.Success).data

            Surface(modifier = Modifier.fillMaxSize()) {
                ReminderScreen(
                    selectedCategory = selectedCategory!!,
                    categories = categories,
                    onCategorySelected = categoryViewModel::onCategorySelected,
                    navigationController = navigationController,
                    mainViewModel = reminderViewModel,
                )
            }
        }
        is CategoryViewState.Error -> {

        }
        is CategoryViewState.Loading -> {

        }
    }

}

@Composable
private fun ReminderScreen(
    selectedCategory: Category,
    categories: List<Category>,
    onCategorySelected: (Category) -> Unit,
    navigationController: NavController,
    mainViewModel: MainViewModel,
) {
    Scaffold (

        topBar = {
            TopBar(
                navigationController = navigationController
            )
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
            CategoryTabs(
                categories = categories,
                selectedCategory = selectedCategory!!,
                onCategorySelected = onCategorySelected,
            )
            Spacer(modifier = Modifier.height(20.dp))

            SortMenu()

            ReminderList(
                selectedCategory = selectedCategory,
                mainViewModel = mainViewModel,
                navigationController = navigationController
            )

        }
    }
}


@Composable
private fun TopBar(
    navigationController: NavController
) {
    TopAppBar(
        backgroundColor = Color(255, 255, 255, 255),
        contentColor = Color(0, 0, 0, 255),
        modifier = Modifier.heightIn(100.dp),
        elevation = 0.dp
    ) {
        Spacer(modifier = Modifier.weight(0.06f))

        SearchBar(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .background(Color(217, 217, 217, 255))
        )

        Spacer(modifier = Modifier.weight(0.06f))

        IconButton(
            onClick = { navigationController.navigate("calendar") }
        ) {
            Icon(
                imageVector = Icons.Outlined.CalendarToday,
                contentDescription = null,
                Modifier.size(50.dp)
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
                Modifier.size(50.dp),
                tint = Color(255, 0, 0, 255)
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
                Modifier.size(50.dp)
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
        onClick = { navigationController.navigate("addreminder") },
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
private fun SearchBar(
    query: String = "",
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    var searchText = remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier.width(300.dp),
        value = searchText.value,
        onValueChange = { text -> searchText.value = text },
        label = {
            Text(
                text = "Search",
                textAlign = TextAlign.Center)
        },
        singleLine = true,
        shape = RoundedCornerShape(corner = CornerSize(30.dp)),
    )
}

@Composable
private fun CategoryTabs(
    categories: List<Category>,
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit
) {
    val selectedIndex = categories.indexOfFirst { it == selectedCategory }
    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        edgePadding = 10.dp,
        indicator = emptyTabIndicator,
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Color(0,0,0,0)
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = index == selectedIndex,
                onClick = { onCategorySelected(category) }
            ) {
                ChoiceChipContent(
                    text = category.name,
                    selected = index == selectedIndex,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 3.dp)
                )
            }

        }
    }
}

private val emptyTabIndicator: @Composable (List<TabPosition>) -> Unit = {}
@Composable
private fun ChoiceChipContent(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        color = when {
            selected -> Color(0,0,0,255)
            else -> Color(217, 217, 217, 255)
        },
        contentColor = when {
            selected -> Color.White
            else -> Color(0, 0, 0, 255)
        },
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
        )
    }
}


@Composable
private fun ReminderList(
    selectedCategory: Category,
    mainViewModel: MainViewModel,
    navigationController: NavController
) {
    if (selectedCategory.name == "All") {
        mainViewModel.loadAllReminders()
    } else {
        mainViewModel.loadRemindersFor(selectedCategory)
    }

    val reminderViewState by mainViewModel.reminderState.collectAsState()

    var isTodayText_ = false
    var isTomorrowText_ = false
    var isUpcomingText_ = false

    when (reminderViewState) {
        is ReminderViewState.Loading -> {}
        is ReminderViewState.Success -> {
            val reminderList = (reminderViewState as ReminderViewState.Success).data

            LazyColumn(
                contentPadding = PaddingValues(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment =  Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                items(reminderList.sortedBy { it.reminderTime }) { item ->

                    // if there are reminders today add word today above them
                    if (item.reminderTime.toLocalDate() == LocalDate.now() && !isTodayText_) {
                        Text(
                            text = stringResource(R.string.today),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h6,
                            fontSize = 25.sp,
                            modifier = Modifier
                                .padding(10.dp)
                        )
                        isTodayText_ = true
                    }
                    if (item.reminderTime.toLocalDate() == LocalDate.now().plusDays(1) && !isTomorrowText_) {
                        Text(
                            text = stringResource(R.string.tomorrow),
                            style = MaterialTheme.typography.h6,
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(10.dp)
                        )
                        isTomorrowText_ = true
                    }
                    if (item.reminderTime.toLocalDate() >= LocalDate.now().plusDays(2) && !isUpcomingText_) {
                        Text(
                            text = stringResource(R.string.upcoming),
                            style = MaterialTheme.typography.h6,
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(10.dp)
                        )
                        isUpcomingText_ = true
                    }

                    if (item.reminderTime.toEpochSecond(ZoneOffset.UTC) > Instant.now().epochSecond) {
                        ReminderListItem(
                            reminder = item,
                            navigationController = navigationController,
                            onClick = { /*TODO*/ },
                            MainViewModel = mainViewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ReminderListItem(
    reminder: Reminder,
    navigationController: NavController,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    MainViewModel: MainViewModel
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(217, 217, 217, 255))
    ) {
        val (title, date, time, icon, delete) = createRefs()

        Text(
            text = reminder.title,
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .padding(10.dp)
        )

        // format the time to be dd.mm.yyyy klo hh:mm
        val reminderTime = reminder.reminderTime
        val reminderTimeFormatted = reminderTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy 'klo' HH:mm"))
        
        Text(
            text = reminderTimeFormatted,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .constrainAs(date) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                }
                .padding(10.dp)
        )

        IconButton(
            onClick = {
                MainViewModel.deleteReminder(reminder)
                      },
            modifier = Modifier
                .constrainAs(delete) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
                .padding(10.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Done,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = Color(0, 0, 0, 255)
            )
        }
    }
}


@Composable
private fun SortMenu(
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    val sortOptions = listOf("Default", "Title", "Date", "Past", "Upcoming")
    val configuration = LocalConfiguration.current
    val dropdown = (configuration.screenWidthDp.dp) - 150.dp

    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { expanded = true })
                .padding(10.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = stringResource(R.string.sort),
                style = MaterialTheme.typography.h6,
                fontSize = 20.sp
            )
            Icon(
                imageVector = Icons.Rounded.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = Color(0, 0, 0, 255)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { expanded = true })
                .padding(10.dp)
                .offset(dropdown,10.dp),
            horizontalArrangement = Arrangement.End
        ) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(130.dp)
                    .offset(10.dp, 0.dp)

            ) {
                sortOptions.forEachIndexed { index, sort ->
                    DropdownMenuItem(onClick = {
                        selectedIndex = index
                        expanded = false
                    }) {
                        Text(
                            text = sort,
                            style = MaterialTheme.typography.body1,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}