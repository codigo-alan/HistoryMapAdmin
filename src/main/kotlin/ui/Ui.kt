package ui

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

    fun notValid() : Boolean {
        println("El ingreso no es válido.")
        return false
    }
}