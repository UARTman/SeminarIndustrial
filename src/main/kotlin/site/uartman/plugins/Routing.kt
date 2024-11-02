package site.uartman.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import site.uartman.model.TaskParams
import site.uartman.model.TaskRepository

fun Application.configureRouting() {
    val taskRepo: TaskRepository by inject()

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        get("/healthcheck") {
            call.respond(HttpStatusCode.OK)
        }
        get("/json") {
            call.respond(mapOf("message" to "Hello world!"))
        }
        route("/tasks") {
            get {
                call.respond(taskRepo.allTasks())
            }

            route("/{taskId}") {
                get {
                    var taskId: Int
                    try {
                        taskId = call.parameters["taskId"]!!.toInt()
                    } catch (_: NumberFormatException) {
                        call.respond(HttpStatusCode.BadRequest)
                        return@get
                    }
                    val task = taskRepo.findTask(taskId)
                    if (task == null) {
                        call.respond(HttpStatusCode.NotFound)
                        return@get
                    }
                    call.respond(task)
                }

                put {
                    var taskId: Int
                    try {
                        taskId = call.parameters["taskId"]!!.toInt()
                    } catch (_: NumberFormatException) {
                        call.respond(HttpStatusCode.BadRequest)
                        return@put
                    }
                    val taskParams = call.receive<TaskParams>()
                    val task = taskRepo.updateTask(taskId, taskParams)
                    if (task == null) {
                        call.respond(HttpStatusCode.NotFound)
                        return@put
                    }
                    call.respond(task)
                }
            }

            post {
                val task = call.receive<TaskParams>()
                call.respond(taskRepo.addTask(task.toTask()))
            }
        }
    }
}
