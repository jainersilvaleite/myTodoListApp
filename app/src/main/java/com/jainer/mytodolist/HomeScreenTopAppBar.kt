package com.jainer.mytodolist

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopAppBar(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel()
) {
    val homeScreenUiState by viewModel.uiState.collectAsState()
    val initialNewTaskTitle = stringResource(R.string.taskTitleTextField_label)
    val initialNewTaskDescription = stringResource(R.string.taskDescriptionTextField_label)

    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.homeScreenTitle),
                fontWeight = FontWeight.Bold
            )
        },
        actions = {
            IconButton(
                onClick = {
                    viewModel.onChangeTaskTitleTextField(title = initialNewTaskTitle)
                    viewModel.onChangeTaskDescriptionTextField(description = initialNewTaskDescription)
                    viewModel.onChangeShowNewTaskDialog()
                }, // shows NewTaskDialog
                modifier = modifier
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.newTask_hint)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        modifier = modifier
    )
    if (homeScreenUiState.showNewTaskDialog) {
        NewTaskDialog(
            onDimissRequest = { viewModel.onChangeShowNewTaskDialog() }
        )
    }
}