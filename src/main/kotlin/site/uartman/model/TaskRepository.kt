package site.uartman.model

interface TaskRepository {
    suspend fun allTasks(): List<Task>
    suspend fun addTask(task: Task): Task
}