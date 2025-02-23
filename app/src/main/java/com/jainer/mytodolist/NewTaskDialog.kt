package com.jainer.mytodolist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jainer.mytodolist.data.Task
import com.jainer.mytodolist.data.TaskPriority
import com.jainer.mytodolist.ui.theme.mulishFontFamily

@Composable
fun NewTaskDialog(
    onDimissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel()
) {
    val homeScreenUiState by viewModel.uiState.collectAsState()
    val taskContainerColor = MaterialTheme.colorScheme.surface
    val onTaskContainerColor = MaterialTheme.colorScheme.onSurface

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
                horizontalArrangement = Arrangement.End,
                modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.newTaskDialog_hint),
                    color = onTaskContainerColor,
                    textAlign = TextAlign.Left,
                    modifier = modifier.weight(0.80f)
                )
                IconButton(
                    onClick = {
                        val newTask = Task(
                            id = homeScreenUiState.tasksIdCounter,
                            title = homeScreenUiState.taskTitleTextField,
                            description = homeScreenUiState.taskDescriptionTextField,
                            priority = homeScreenUiState.selectedPriority,
                            isChecked = false
                        )
                        viewModel.onNewTask(newTask)
                        viewModel.onChangeTasksIdCounter()
                        viewModel.onChangeShowNewTaskDialog()
                    }, // new task confirmation
                    modifier = modifier.weight(0.10f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(R.string.closeNewTask_hint),
                        tint = onTaskContainerColor
                    )
                }
                IconButton(
                    onClick = onDimissRequest, // close new
                    modifier = modifier.weight(0.10f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.closeNewTask_hint),
                        tint = onTaskContainerColor
                    )
                }
            }
            OutlinedTextField(
                value = homeScreenUiState.taskTitleTextField,
                onValueChange = { viewModel.onChangeTaskTitleTextField(it) },
                modifier = modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = onTaskContainerColor,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    errorBorderColor = MaterialTheme.colorScheme.error,
                    errorCursorColor = MaterialTheme.colorScheme.error,
                    errorLabelColor = MaterialTheme.colorScheme.error,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = onTaskContainerColor,
                    unfocusedTextColor = onTaskContainerColor,
                    focusedLabelColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent
                ),
                singleLine = true,
                shape = RectangleShape,
                placeholder = {
                    Text(
                        text = stringResource(R.string.taskTitleTextField_label),
                        color = onTaskContainerColor,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                label = {
                    Text(
                        text = stringResource(R.string.taskTitleTextField_label),
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                textStyle = TextStyle(
                    fontFamily = mulishFontFamily,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = onTaskContainerColor,
                    fontSize = 22.sp,
                    lineHeight = 28.sp,
                    letterSpacing = 0.sp
                )
            )
            OutlinedTextField(
                value = homeScreenUiState.taskDescriptionTextField,
                onValueChange = { viewModel.onChangeTaskDescriptionTextField(it) },
                modifier = modifier.fillMaxWidth().weight(0.60f),
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = onTaskContainerColor,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    errorBorderColor = MaterialTheme.colorScheme.error,
                    errorCursorColor = MaterialTheme.colorScheme.error,
                    errorLabelColor = MaterialTheme.colorScheme.error,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = onTaskContainerColor,
                    unfocusedTextColor = onTaskContainerColor,
                    focusedLabelColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent
                ),
                shape = RectangleShape,
                placeholder = {
                    Text(
                        text = stringResource(R.string.taskDescriptionTextField_label),
                        color = onTaskContainerColor,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                label = {
                    Text(
                        text = stringResource(R.string.taskDescriptionTextField_label),
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                textStyle = TextStyle(
                    fontFamily = mulishFontFamily,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start,
                    color = onTaskContainerColor,
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 0.5.sp
                )
            )
            Text(
                text = stringResource(R.string.taskPriorityOptions_hint),
                color = onTaskContainerColor,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = modifier.fillMaxWidth().padding(bottom = 5.dp)
            )
            val priorityOptions = TaskPriority.entries.toList()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = modifier.fillMaxWidth()
            ) {
                priorityOptions.forEach {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        RadioButton(
                            selected = (it == homeScreenUiState.selectedPriority),
                            onClick = { viewModel.onChangeSelectedPriority(it) }
                        )
                        Text(
                            text = "%s".format(stringResource(viewModel.taskPriorityToStringRes(it))),
                        )
                    }
                }
            }
        }
    }
}