package database

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import models.Category
import models.MarkerEntity

class MarkerRepository(val realm: Realm, val realmApp: App) {

    fun markersListFlow() : Flow<List<MarkerEntity>> = realm.query<MarkerEntity>().find().asFlow().map { it.list.toList() }
    fun markersByCategory(categoryName: String) : Flow<List<MarkerEntity>> = realm.query<MarkerEntity>("category.name == '${categoryName}'").find().asFlow().map { it.list.toList() }
    fun allUsers() = realmApp.allUsers()

    //TODO delete user
    suspend fun deleteUser(mapOfUsers : Map<String, User>, userToDeleteId : String){
        mapOfUsers.forEach { (key, value) ->
            if (key == userToDeleteId) {
                value.delete()
                println("Debug: ${value.state}") //state should be REMOVED
            } //if key of map equal inputted id, delete its value which is a User
        }
    }
    //TODO create Category
    //TODO delete Category

}