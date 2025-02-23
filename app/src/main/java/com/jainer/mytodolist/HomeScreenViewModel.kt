package com.jainer.mytodolist

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.jainer.mytodolist.data.Task
import com.jainer.mytodolist.data.TaskPriority
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeScreenViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    fun onChangeShowNewTaskDialog() {
        val showNewTaskDialog = _uiState.value.showNewTaskDialog
        _uiState.value = _uiState.value.copy(
            showNewTaskDialog = !showNewTaskDialog
        )
    }

    fun onChangeShowEditTaskDialog() {
        val showEditTaskDialog = _uiState.value.showEditTaskDialog
        _uiState.value = _uiState.value.copy(
            showEditTaskDialog = !showEditTaskDialog
        )
    }

    fun onChangeTaskTitleTextField(title: String) {
        _uiState.value = _uiState.value.copy(
            taskTitleTextField = title
        )
    }

    fun onChangeTaskDescriptionTextField(description: String) {
        _uiState.value = _uiState.value.copy(
            taskDescriptionTextField = description
        )
    }

    fun onChangeSelectedPriority(taskPriority: TaskPriority) {
        _uiState.value = _uiState.value.copy(
            selectedPriority = taskPriority
        )
    }

    fun onChangeLatestTask(task: Task) {
        _uiState.value = _uiState.value.copy(
            latestTask = task
        )
    }

    fun onChangeShowTaskDialog() {
        val showTaskDialog = _uiState.value.showTaskDialog
        _uiState.value = _uiState.value.copy(
            showTaskDialog = !showTaskDialog
        )
    }

    fun onChangeTasksIdCounter() {
        val tasksIdCounter = _uiState.value.tasksIdCounter
        _uiState.value = _uiState.value.copy(tasksIdCounter = tasksIdCounter + 1)
    }

    fun onNewTask(task: Task) {
        val tasksList = _uiState.value.tasksList
        _uiState.value = _uiState.value.copy(
            tasksList = _sortTasksList(
                tasksListToSort = tasksList + task
            )
        )
    }

    fun onEditTask(taskToEdit: Task, editedTask: Task) {
        val tasksList = _uiState.value.tasksList.filterNot { it.id == taskToEdit.id }

        _uiState.value = _uiState.value.copy(
            tasksList = _sortTasksList(
                tasksListToSort = tasksList + editedTask
            )
        )
    }

    fun onDeleteTask(task: Task) {
        val tasksList = _uiState.value.tasksList
        _uiState.value = _uiState.value.copy(
            tasksList = _sortTasksList(
                tasksListToSort = tasksList.filterNot {
                    it.id == task.id
                }
            )
        )
    }

    @StringRes
    fun taskPriorityToStringRes(taskPriority: TaskPriority): Int {
        return when(taskPriority) {
            TaskPriority.HIGH -> R.string.highPriority_hint
            TaskPriority.MEDIUM -> R.string.mediumPriority_hint
            else -> R.string.lowPriority_hint
        }
    }

    private fun _sortTasksList(tasksListToSort: List<Task>): List<Task> {
        return tasksListToSort.sortedWith(
            compareBy<Task> { it.isChecked }
                .thenBy { it.priority }
        )
    }

}