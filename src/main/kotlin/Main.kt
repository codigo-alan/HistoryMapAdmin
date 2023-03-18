import database.MarkerRepository
import database.RealmRepo
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.AppConfiguration
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.subscriptions
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import models.Category
import models.MarkerEntity
import ui.Ui
import java.util.*

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
                "All Category"
            )

        }
        .waitForInitialRemoteData()
        .build()


    val realm = Realm.open(config)
    realm.subscriptions.waitForSynchronization()*/

    /**
     * Instantiate RealmRepo, MarkerRepository and Ui
     */
    val scanner = Scanner(System.`in`)
    val ui = Ui(scanner)
    val realmRepo = RealmRepo()
    lateinit var markerRepository : MarkerRepository

    val userSelect = ui.initialMenu()
    val userInputs = ui.userInputs()
    val userAction = when (userSelect) {
        1 -> loginUser(userInputs, realmRepo)
        2 -> registerUser(userInputs, realmRepo)
        else -> ui.notValid()
    }

    if (userAction) {
        markerRepository = MarkerRepository()
    }


}

suspend fun registerUser(userInputs: List<String>, realmRepo: RealmRepo) : Boolean {
    return try {

        realmRepo.register(userInputs.first(), userInputs.last())
        true //without errors

    }catch(e: Exception){

        println("No fue posible registrar el usuario. Error: $e")
        false //with some error

    }
}

suspend fun loginUser(userInputs: List<String>, realmRepo: RealmRepo) : Boolean {
    return try {
        println("${userInputs.first()} , ${userInputs.last()}")

        realmRepo.login(userInputs.first(), userInputs.last())
        true //without errors

    }catch(e: Exception){

        println("No fue posible loguearse. Error: $e")
        false //with some error

    }
}
