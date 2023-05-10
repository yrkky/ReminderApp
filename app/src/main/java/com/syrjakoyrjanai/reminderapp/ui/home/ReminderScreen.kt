package com.syrjakoyrjanai.reminderapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.syrjakoyrjanai.core.domain.entity.Category
import com.syrjakoyrjanai.core.domain.entity.Reminder
import com.syrjakoyrjanai.reminderapp.ui.category.CategoryViewModel
import com.syrjakoyrjanai.reminderapp.ui.category.CategoryViewState
import com.syrjakoyrjanai.reminderapp.ui.reminder.MainViewModel

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
private fun ReminderScreen(
    selectedCategory: Category,
    categories: List<Category>,
    onCategorySelected: (Category) -> Unit,
    navigationController: NavController,
    mainViewModel: MainViewModel
) {
    Scaffold (
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                modifier = Modifier
                    .size(90.dp),
                backgroundColor = Color(217,217,217, 255),
                elevation = FloatingActionButtonDefaults.elevation(0.dp)
            ) {
            }
            FloatingActionButton(
                onClick = { /*TODO*/ },
                contentColor = Color(217,217,217, 255),
                modifier = Modifier
                    .padding(10.dp)
                    .size(70.dp),
                backgroundColor = Color(255,255,255, 255)
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

        topBar = {
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
        },


        bottomBar = {
            BottomAppBar(
                backgroundColor = Color(217, 217, 217, 255),
                contentColor = Color(0, 0, 0, 255),
                modifier = Modifier.heightIn(70.dp),


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
                        Modifier.size(50.dp)
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
                        tint = Color(255,0,0,255)
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
                Spacer(modifier = Modifier.weight(0.06f))
            }
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