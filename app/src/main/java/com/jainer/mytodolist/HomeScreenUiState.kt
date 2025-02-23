package com.jainer.mytodolist

import com.jainer.mytodolist.data.Task
import com.jainer.mytodolist.data.TaskPriority

data class HomeScreenUiState(
    val showEditTaskDialog: Boolean = false,
    val showNewTaskDialog: Boolean = false,
    val taskTitleTextField: String = "",
    val taskDescriptionTextField: String = "",
    val selectedPriority: TaskPriority = TaskPriority.LOW,
    val showTaskDialog: Boolean = false,
    val latestTask: Task = Task(),
    val tasksList: List<Task> = emptyList(),
    val tasksIdCounter: Int = 0
)
