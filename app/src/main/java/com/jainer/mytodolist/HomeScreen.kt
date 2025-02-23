package com.jainer.mytodolist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jainer.mytodolist.ui.theme.MyToDoListTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel()
) {
    val homeScreenUiState by viewModel.uiState.collectAsState()
    val tasksList = homeScreenUiState.tasksList

    Scaffold(
        topBar = { HomeScreenTopAppBar() },
        modifier = modifier
    ) {
        if (tasksList.isEmpty()) {
            Column(
                modifier = modifier.padding(it)
            ) {
                Spacer(modifier = modifier.height(10.dp))
                Text(
                    text = stringResource(R.string.noTasks_hint),
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = modifier.fillMaxWidth()
                )
            }
        } else {
            LazyColumn(
                modifier = modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                item {
                    Spacer(modifier = modifier.fillMaxWidth().height(8.dp))
                }
                tasksList.forEachIndexed { _, task ->
                    item {
                        HomeScreenTaskContainer(
                            task = task,
                            onClick = {
                                viewModel.onChangeLatestTask(task)
                                viewModel.onChangeShowTaskDialog()
                            }
                        )
                    }
                }
            }
            if (homeScreenUiState.showTaskDialog) {
                TaskDialog(
                    task = homeScreenUiState.latestTask,
                    onDimissRequest = { viewModel.onChangeShowTaskDialog() }
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview(modifier: Modifier = Modifier) {
    MyToDoListTheme {
        HomeScreen()
    }
}