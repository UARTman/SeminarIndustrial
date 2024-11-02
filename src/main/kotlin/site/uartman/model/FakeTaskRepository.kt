package site.uartman.model

class FakeTaskRepository : TaskRepository {
    private var nextId = 2

    private val tasks = mutableListOf(
        Task(0, "Walk the dog", true),
        Task(1, "Eat my lunch", false),
    )

    override suspend fun allTasks(): List<Task> {
        return tasks
    }

    override suspend fun addTask(task: Task): Task {
        var task2 = Task(
            nextId,
            task.text,
            task.done
        )
        nextId += 1
        tasks.add(task2)
        return task
    }

    override suspend fun updateTask(id: Int, task: TaskParams): Task? {
        for (i in 0..tasks.size) {
            if (tasks[i].id == id) {
                tasks[i] = Task(id, task.text, task.done)
                return tasks[i]
            }
        }
        return null
    }

    override suspend fun findTask(id: Int): Task? {
        return tasks.find {
            it.id == id
        }
    }
}