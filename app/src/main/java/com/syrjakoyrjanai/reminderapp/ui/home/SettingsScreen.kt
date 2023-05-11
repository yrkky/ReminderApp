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
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.syrjakoyrjanai.core.domain.entity.Category
import com.syrjakoyrjanai.reminderapp.R
import com.syrjakoyrjanai.reminderapp.ui.category.CategoryViewModel
import com.syrjakoyrjanai.reminderapp.ui.category.CategoryViewState
import com.syrjakoyrjanai.reminderapp.ui.reminder.MainViewModel


@Composable
fun Settings(
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
                SettingsScreen(
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
private fun SettingsScreen(
    selectedCategory: Category,
    categories: List<Category>,
    onCategorySelected: (Category) -> Unit,
    navigationController: NavController,
    mainViewModel: MainViewModel
) {
    Scaffold (

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
    val halfScreenWidth = (configuration.screenWidthDp.dp)/2 - 50.dp

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