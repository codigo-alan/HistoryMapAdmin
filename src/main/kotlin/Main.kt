import database.RealmRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ui.Ui
import java.util.*


/**
 * Instantiate RealmRepo, Ui
 */
val scanner = Scanner(System.`in`)
val ui = Ui(scanner)
val realmRepo = RealmRepo()
suspend fun main() {

    /*val realmApp = App.create(
        AppConfiguration.Builder("application-0-qderj") //app id from app services in atlas.
            .log(LogLevel.ALL)
            .build())
    val scanner = Scanner(System.`in`)
    println("User:")
    val userName = scanner.nextLine()
    println("Password:")
    val userPassword = scanner.nextLine()



    val creds = Credentials.emailPassword(userName, userPassword)
    realmApp.login(creds)
    val user = realmApp.currentUser!!
    println(user.loggedIn)

    //remote config
    val config = SyncConfiguration.Builder(user, setOf(MarkerEntity::class, Category::class))
        .initialSubscriptions { realm ->
            add(
                realm.query<MarkerEntity>(),
                "All Markers"
            )
            add(
                realm.query<Category>(),
                "All Categories"
            )

        }
        .waitForInitialRemoteData()
        .build()


    val realm = Realm.open(config)
    realm.subscriptions.waitForSynchronization()*/



    /**
     * User keyboard selects
     */
    val userSelect = ui.initialMenu()
    val userInputs = ui.userInputs()
    val userActionResult = when (userSelect) {
        1 -> loginUser(userInputs)
        2 -> registerUser(userInputs)
        else -> ui.notValid()
    }

    if (userActionResult) {
        val userDashboardSelect = ui.userDashboard()
        when (userDashboardSelect) {
            1 -> getAllMarkers()
            2 -> getAllUsers()
            3 -> deleteUser()
            4 -> filterMarkerByCategory()
            else -> ui.notValid()
        }
    }


}

fun filterMarkerByCategory() {
    val categoryName = scanner.nextLine()
    val listOfMarkers = realmRepo.markerRepository.markersByCategory(categoryName)
    CoroutineScope(Dispatchers.Main).launch{
        ui.showListOfMarkers(listOfMarkers)
    }
}

fun deleteUser() {
    val mapOfUsers = realmRepo.markerRepository.allUsers()
    val userToDeleteId = scanner.nextLine() //type the id of user to delete
    CoroutineScope(Dispatchers.Main).launch {
        realmRepo.markerRepository.deleteUser(mapOfUsers, userToDeleteId)
    }
}

fun getAllUsers() {
    val mapOfUsers = realmRepo.markerRepository.allUsers()
    ui.showUsers(mapOfUsers)
}

fun getAllMarkers() {
    val listOfMarkers = realmRepo.markerRepository.markersListFlow() //TODO not live data
    CoroutineScope(Dispatchers.Main).launch{
        ui.showListOfMarkers(listOfMarkers)
    }


}

suspend fun registerUser(userInputs: List<String>) : Boolean {
    return try {

        realmRepo.register(userInputs.first(), userInputs.last())
        true //without errors

    }catch(e: Exception){

        println("No fue posible registrar el usuario. Error: $e")
        false //with some error

    }
}

suspend fun loginUser(userInputs: List<String>) : Boolean {
    return try {
        println("${userInputs.first()} , ${userInputs.last()}")

        realmRepo.login(userInputs.first(), userInputs.last())
        true //without errors

    }catch(e: Exception){

        println("No fue posible loguearse. Error: $e")
        false //with some error

    }
}
