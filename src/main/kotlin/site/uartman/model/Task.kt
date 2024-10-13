package site.uartman.model

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: Int,
    val text: String,
    val done: Boolean
)

@Serializable
data class TaskParams(
    val text: String,
    val done: Boolean,
) {
    fun toTask(): Task = Task(0, text, done)
}