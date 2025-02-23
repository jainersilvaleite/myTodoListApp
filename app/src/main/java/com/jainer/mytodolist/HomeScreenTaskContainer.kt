package com.jainer.mytodolist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jainer.mytodolist.data.Task
import com.jainer.mytodolist.data.TaskPriority
import com.jainer.mytodolist.ui.theme.checked_task
import com.jainer.mytodolist.ui.theme.high_priority_task
import com.jainer.mytodolist.ui.theme.medium_priority_task
import com.jainer.mytodolist.ui.theme.on_checked_task
import com.jainer.mytodolist.ui.theme.on_high_priority_task
import com.jainer.mytodolist.ui.theme.on_medium_priority_task

@Composable
fun HomeScreenTaskContainer(
    task: Task,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel()
) {
    val checkedBox = painterResource(R.drawable.baseline_check_box_24)
    val unCheckedBox = painterResource(R.drawable.baseline_check_box_outline_blank_24)
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
    val taskTextDecoration =
        if (task.isChecked) TextDecoration.LineThrough
        else TextDecoration.None

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 8.dp)
            .background(
                color = taskContainerColor,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier.fillMaxWidth().padding(10.dp)
        ) {
            Column(
                modifier = modifier.weight(0.9f).clickable(onClick = onClick)
            ) {
                Text(
                    text = task.title,
                    color = onTaskContainerColor,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left,
                    textDecoration = taskTextDecoration

                )
                Text(
                    maxLines = 3,
                    text = task.description,
                    color = onTaskContainerColor,
                    textAlign = TextAlign.Left,
                    textDecoration = taskTextDecoration,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            IconToggleButton(
                checked = task.isChecked,
                modifier = modifier.weight(0.1f),
                onCheckedChange = {
                    val checkedTask = Task(
                        id = task.id,
                        title = task.title,
                        description = task.description,
                        priority = task.priority,
                        isChecked = !task.isChecked
                    )
                    viewModel.onEditTask(
                        taskToEdit = task,
                        editedTask = checkedTask
                    )
                } // check this task
            ) {
                Icon(
                    painter = if (!task.isChecked) unCheckedBox else checkedBox,
                    contentDescription = stringResource(R.string.taskCheck_hint).format(task.title),
                    tint = onTaskContainerColor
                )
            }
        }
    }
}