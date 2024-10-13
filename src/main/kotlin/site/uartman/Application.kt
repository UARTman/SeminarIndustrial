package site.uartman

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import site.uartman.db.Tasks
import site.uartman.model.DbTaskRepository
import site.uartman.model.FakeTaskRepository
import site.uartman.model.TaskRepository
import site.uartman.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    EngineMain.main(args)
//    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
//        .start(wait = true)
}

val fakeTaskModule = module {
    single<TaskRepository> { FakeTaskRepository() }
}

val dbModule = module {
    single<TaskRepository> { DbTaskRepository() }
}

fun Application.module() {
    install(Koin) {
        slf4jLogger()
        modules(dbModule)
    }
    configureDatabases()
    transaction {
        SchemaUtils.create(Tasks)
    }
    configureSerialization()
    configureRouting()
}
