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
import java.util.*

suspend fun main() {
    /**
     * Create the App with the id
     */
    val realmApp = App.create(
        AppConfiguration.Builder("application-0-qderj") //app id from app services in atlas.
            .log(LogLevel.ALL)
            .build())

    /**
     * Scanners
     */
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
    realm.subscriptions.waitForSynchronization()
}