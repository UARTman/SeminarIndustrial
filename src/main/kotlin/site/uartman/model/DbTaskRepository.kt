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

}