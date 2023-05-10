package com.syrjakoyrjanai.reminderapp.ui.home


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Card
import androidx.compose.material.Divider
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
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.syrjakoyrjanai.reminderapp.ui.category.CategoryViewModel
import com.syrjakoyrjanai.reminderapp.ui.category.CategoryViewState
import com.syrjakoyrjanai.core.domain.entity.Category
import com.syrjakoyrjanai.core.domain.entity.Reminder
import com.syrjakoyrjanai.reminderapp.R
import com.syrjakoyrjanai.reminderapp.ui.reminder.MainViewModel
import com.syrjakoyrjanai.reminderapp.ui.reminder.ReminderViewState
import kotlin.concurrent.timerTask


@Composable
fun Home(
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
                HomeScreen(
                    selectedCategory = selectedCategory!!,
                    categories = categories,
                    onCategorySelected = categoryViewModel::onCategorySelected,
                    navigationController = navigationController,
                    mainViewModel = reminderViewModel
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
fun HomeScreen(
    selectedCategory: Category,
    categories: List<Category>,
    onCategorySelected: (Category) -> Unit,
    navigationController: NavController,
    mainViewModel: MainViewModel
){
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
                selectedCategory = selectedCategory,
                onCategorySelected = onCategorySelected,
            )

            upcomingReminders(
                selectedCategory = selectedCategory,
                navigationController = navigationController,
                mainViewModel = mainViewModel
            )

            importantReminders(
                selectedCategory = selectedCategory,
                navigationController = navigationController,
                mainViewModel = mainViewModel
            )
            quickNoteBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )

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
            text = stringResource(R.string.search),
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
            onClick = { /*TODO*/ }
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
                // Already in home?
                // navigationController.navigate("home")
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.Home,
                contentDescription = null,
                Modifier.size(50.dp),
                tint = Color(255, 0, 0, 255)
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
    FloatingActionButton(
        onClick = { },
        modifier = Modifier
            .size(90.dp)
            .offset(160.dp, (-40).dp),
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
            .offset(160.dp, (-40).dp),
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


@Composable
private fun upcomingReminders(
    selectedCategory: Category,
    mainViewModel: MainViewModel,
    navigationController: NavController
){
    mainViewModel.loadRemindersFor(selectedCategory)

    val reminderViewState by mainViewModel.reminderState.collectAsState()

    when (reminderViewState) {
        is ReminderViewState.Loading -> {}
        is ReminderViewState.Success -> {
            val reminderList = (reminderViewState as ReminderViewState.Success).data

            Text(
                text = stringResource(R.string.upcoming),
                style = MaterialTheme.typography.h6
            )

            LazyRow(
                contentPadding = PaddingValues(0.dp),
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(reminderList) { item ->

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

@Composable
private fun ReminderListItem(
    reminder: Reminder,
    navigationController: NavController,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    MainViewModel: MainViewModel
) {
    Card(
        modifier = modifier
            .padding(10.dp)
            .size(100.dp),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color(217,217,217, 255)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    MainViewModel.setEditReminder(reminder)
                    navigationController.navigate("addreminder")
                }
        ) {
            Text(
                text = reminder.title,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun importantReminders(
    selectedCategory: Category,
    mainViewModel: MainViewModel,
    navigationController: NavController
){
    mainViewModel.loadRemindersFor(selectedCategory)

    val reminderViewState by mainViewModel.reminderState.collectAsState()

    when (reminderViewState) {
        is ReminderViewState.Loading -> {}
        is ReminderViewState.Success -> {
            val reminderList = (reminderViewState as ReminderViewState.Success).data

            Text(
                text = stringResource(R.string.important),
                style = MaterialTheme.typography.h6
            )

            LazyRow(
                contentPadding = PaddingValues(0.dp),
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(reminderList) { item ->

                    importantReminderListItem(
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

@Composable
private fun importantReminderListItem(
    reminder: Reminder,
    navigationController: NavController,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    MainViewModel: MainViewModel
) {
    Card(
        modifier = modifier
            .padding(10.dp)
            .size(100.dp),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color(217,217,217, 255)
    ) {
        ConstraintLayout(
            modifier = modifier
                .clickable {
                    MainViewModel.setEditReminder(reminder)
                    navigationController.navigate("addreminder")
                }
                .fillMaxWidth(),

            ) {
            val (dividerRef, iconRef, titleRef, messageRef, dateRef) = createRefs()
            Divider(

                Modifier.constrainAs(dividerRef) {
                    top.linkTo(parent.top)
                    centerHorizontallyTo(parent)
                    width = Dimension.fillToConstraints
                }
            )

            Text(
                text = reminder.title,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.constrainAs(titleRef) {
                       linkTo(
                           start = parent.start,
                           end = parent.end,
                           startMargin = 0.dp,
                           endMargin = 4.dp
                       )
                    },
                textAlign = TextAlign.Center
            )

            Icon(
                imageVector = Icons.Rounded.Star,
                contentDescription = null,
                modifier = Modifier.size(23.dp).constrainAs(iconRef) {
                    linkTo(
                        start = parent.start,
                        end = parent.end,
                        startMargin = 75.dp,
                        endMargin = 0.dp,
                        bias = 0f
                    )
                }
            )
        }
    }
}


@Composable
private fun quickNoteBox(
    modifier: Modifier = Modifier
) {
    var textbox = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(
            text = stringResource(R.string.quick_notes),
            style = MaterialTheme.typography.h6,
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f).background(Color(217, 217, 217, 255)),
            value = textbox.value,
            onValueChange = { text -> textbox.value = text },
            label = {
                Text(
                    text = "",
                    textAlign = TextAlign.Center)
            },
            shape = RoundedCornerShape(corner = CornerSize(10.dp)),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(217,217,217, 255),
                unfocusedBorderColor = Color(217,217,217, 255),
                focusedLabelColor = Color(0,0,0, 255),
                unfocusedLabelColor = Color(0,0,0, 255),
                cursorColor = Color(0,0,0, 255),
                textColor = Color(0,0,0, 255)
            )
        )
    }

}