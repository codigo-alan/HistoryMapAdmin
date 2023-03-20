package ui

import models.Category
import models.MarkerEntity
import java.util.*

class Ui(val scanner: Scanner) {
    fun userDashboard() : Int {
        println(
            "¿Qué deseas hacer?:\n" +
                    "1- Lista de Markers\n" +
                    "2- Lista de categorías\n" +
                    "3- Markers por categoría\n" +
                    "4- Agregar categoría\n" +
                    "5- Borrar Marker\n" +
                    "6- Borrar categoría\n" +
                    "0- Salir"
        )
        return scanner.nextLine().toInt()
    }

    fun notValid() : Boolean {
        println("El ingreso no es válido.")
        return false
    }

    fun showListOfMarkers(data: List<MarkerEntity>) {

        data.forEach { markerEntity: MarkerEntity -> println(markerEntity.toString()) }

    }

    fun showListOfCategories(listOfCategories: List<Category>) {
        listOfCategories.forEach { category: Category ->  println(category.toString()) }
    }

}