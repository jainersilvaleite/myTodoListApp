package com.jainer.mytodolist

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jainer.mytodolist.data.Task
import com.jainer.mytodolist.data.TaskPriority
import com.jainer.mytodolist.ui.theme.MyToDoListTheme
import com.jainer.mytodolist.ui.theme.checked_task
import com.jainer.mytodolist.ui.theme.high_priority_task
import com.jainer.mytodolist.ui.theme.medium_priority_task
import com.jainer.mytodolist.ui.theme.on_checked_task
import com.jainer.mytodolist.ui.theme.on_high_priority_task
import com.jainer.mytodolist.ui.theme.on_medium_priority_task

@Composable
fun TaskDialog(
    task: Task,
    onDimissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel()
) {
    val homeScreenUiState by viewModel.uiState.collectAsState()
    val taskContainerColor =
        if (task.isChecked) checked_task
        else when(task.priority) {
            TaskPriority.HIGH -> high_priority_task
            TaskPriority.MEDIUM -> medium_priority_task
            else -> MaterialTheme.colorScheme.surface
        }
    val onTaskContainerColor = when(taskContainerColor) {
        checked_task -> on_checked_task
        high_priority_task -> on_high_priority_task
        medium_priority_task -> on_medium_priority_task
        else -> MaterialTheme.colorScheme.onSurface
    }

    Dialog(
        onDismissRequest = onDimissRequest,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = modifier
                .fillMaxSize(0.90f)
                .background(
                    color = taskContainerColor,
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text =
                        if (task.isChecked) stringResource(R.string.checkedTask_hint)
                        else stringResource(R.string.taskPriority_hint)
                            .format(stringResource(viewModel.taskPriorityToStringRes(taskPriority = task.priority))),
                    color = onTaskContainerColor,
                    textAlign = TextAlign.Left,
                    modifier = modifier.weight(0.70f)
                )
                IconButton(
                    onClick = {
                        viewModel.onChangeShowTaskDialog()
                        viewModel.onDeleteTask(task)
                    }, // delete this task
                    modifier = modifier.weight(0.10f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.deleteTask_hint).format(task.title),
                        tint = onTaskContainerColor
                    )
                }
                IconButton(
                    onClick = {
                        viewModel.onChangeTaskTitleTextField(task.title)
                        viewModel.onChangeTaskDescriptionTextField(task.description)
                        viewModel.onChangeShowEditTaskDialog()
                    }, // edit this task
                    modifier = modifier.weight(0.10f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.editTask_hint).format(task.title),
                        tint = onTaskContainerColor
                    )
                }
                IconButton(
                    onClick = onDimissRequest, // close task
                    modifier = modifier.weight(0.10f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.closeTask_hint).format(task.title),
                        tint = onTaskContainerColor
                    )
                }
            }
            Text(
                text = task.title,
                modifier = modifier.fillMaxWidth(),
                color = onTaskContainerColor,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = modifier.height(8.dp))
            LazyColumn {
                item {
                    Text(
                        text = task.description,
                        modifier = modifier.fillMaxWidth(),
                        color = onTaskContainerColor,
                        textAlign = TextAlign.Left,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            Spacer(modifier = modifier.height(8.dp))
        }
    }
    if (homeScreenUiState.showEditTaskDialog) {
        EditTaskDialog(
            task = task,
            onDimissRequest = { viewModel.onChangeShowEditTaskDialog() },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TaskDialogPreview() {
    MyToDoListTheme {
        TaskDialog(
            task = Task(
                title = "Título da tarefa",
                description = "Descrição da tarefa"
            ), onDimissRequest = {}
        )
    }
}