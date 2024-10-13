package site.uartman.plugins

import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.Database


fun Application.configureDatabases() {
    val host = environment.config.property("postgres.host").getString()
    val user = environment.config.property("postgres.user").getString()
    val password = environment.config.property("postgres.password").getString()
    Database.connect(
        "jdbc:postgresql://$host:5432/seminar_industrial",
        user = user,
        password = password
    )
}