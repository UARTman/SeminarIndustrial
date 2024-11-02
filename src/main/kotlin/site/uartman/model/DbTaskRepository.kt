package site.uartman.model

import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import site.uartman.db.TaskDAO
import site.uartman.db.Tasks
import site.uartman.db.daoToModel

class DbTaskRepository : TaskRepository {
    override suspend fun allTasks(): List<Task> {
        return transaction {
            TaskDAO.all().map(::daoToModel).toList()
        }
    }

    override suspend fun addTask(task: Task): Task {
        val task = transaction {
            TaskDAO.new {
                text = task.text
                done = task.done
            }
        }
        return daoToModel(task)
    }

    override suspend fun updateTask(id: Int, task: TaskParams): Task? {
        return transaction {
            TaskDAO.findByIdAndUpdate(id) {
                it.text = task.text
                it.done = task.done
            }
        }?.let(::daoToModel)
    }

    override suspend fun findTask(id: Int): Task? {
        return transaction {
            TaskDAO.findById(id)
        }?.let(::daoToModel)
    }
}