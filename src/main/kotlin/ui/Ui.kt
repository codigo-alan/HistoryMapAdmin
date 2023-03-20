package ui

import io.realm.kotlin.mongodb.User
import kotlinx.coroutines.flow.Flow
import models.MarkerEntity
import java.util.*

class Ui(val scanner: Scanner) {

    fun initialMenu(): Int {
        println(
            "Selecciona una opción:\n" +
                    "1- Login\n" +
                    "2- Register\n"
        )
        return scanner.nextLine().toInt()
    }

    fun userInputs() : List<String>{
        println("User:")
        val userName = scanner.nextLine()
        println("Password:")
        val userPassword = scanner.nextLine()
        return listOf(userName, userPassword)
    }

    fun userDashboard() : Int {
        println(
            "¿Qué deseas hacer?:\n" +
                    "1- Lista de Markers\n" +
                    "2- Lista de usuarios\n" +
                    "3- Borrar usuario\n" +
                    "4- Filtrar Marker por categoría\n"
        )
        return scanner.nextLine().toInt()
    }

    fun notValid() : Boolean {
        println("El ingreso no es válido.")
        return false
    }

    suspend fun showListOfMarkers(data: Flow<List<MarkerEntity>>) {
        data.collect{
            it.forEach { markerEntity: MarkerEntity -> println(markerEntity.toString()) }
        }
    }
    fun showUsers(users: Map<String, User>){
        users.forEach { (key, value) -> println("$key, $value") }
    }
}