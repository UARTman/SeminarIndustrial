package site.uartman.model

class FakeTaskRepository : TaskRepository {
    private val tasks = mutableListOf(
        Task(0, "Walk the dog", true),
        Task(1, "Eat my lunch", false),
    )

    override suspend fun allTasks(): List<Task> {
        return tasks
    }

    override suspend fun addTask(task: Task): Task {
        tasks.add(task)
        return task
    }
}