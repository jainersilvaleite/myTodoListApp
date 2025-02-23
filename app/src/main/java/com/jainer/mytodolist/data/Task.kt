package com.jainer.mytodolist.data

data class Task(
    val id: Int = -1,
    val title: String = "",
    val description: String = "",
    val priority: TaskPriority = TaskPriority.LOW,
    val isChecked: Boolean = false
)

enum class TaskPriority {
    HIGH,
    MEDIUM,
    LOW
}