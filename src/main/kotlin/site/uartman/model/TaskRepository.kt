package site.uartman.model

interface TaskRepository {
    suspend fun allTasks(): List<Task>
    suspend fun addTask(task: Task): Task
    suspend fun updateTask(id: Int, taskParams: TaskParams): Task?
    suspend fun findTask(id: Int): Task?
//    suspend fun deleteTask(task: Task)
}