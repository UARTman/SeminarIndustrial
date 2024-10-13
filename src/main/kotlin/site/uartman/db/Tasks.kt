package site.uartman.db

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import site.uartman.model.Task

object Tasks: IntIdTable() {
    val text = varchar("text", 50)
    val done = bool("done")
}

class TaskDAO(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<TaskDAO>(Tasks)
    var text by Tasks.text
    var done by Tasks.done
}

fun daoToModel(dao: TaskDAO) = Task(
    dao.id.value,
    dao.text,
    dao.done
)

//class StarWarsFilm(id: EntityID<Int>) : IntEntity(id) {
//    companion object : IntEntityClass<StarWarsFilm>(StarWarsFilms)
//    var sequelId by StarWarsFilms.sequelId
//    var name     by StarWarsFilms.name
//    var director by StarWarsFilms.director
//}