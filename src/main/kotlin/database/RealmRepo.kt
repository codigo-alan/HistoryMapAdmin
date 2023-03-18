package database

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.*
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import models.Category
import models.MarkerEntity

class RealmRepo {

    var realmApp : App
    var user : User? = null
    var realm : Realm? = null
    lateinit var configuration: SyncConfiguration

    init {
        realmApp = createRealmApp()
        user = realmApp.currentUser
    }

    private fun createRealmApp(): App {
        return App.create(
            AppConfiguration.Builder("application-0-qderj") //app id from app services in atlas.
                .log(LogLevel.ALL)
                .build())
    }

    private suspend fun remoteConfig(){
        configuration = SyncConfiguration.Builder(this.user!!, setOf(MarkerEntity::class, Category::class))
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


        realm = Realm.open(this.configuration)
        realm!!.subscriptions.waitForSynchronization()

        //TODO create MarkerRepo
    }

    private fun createCredentials(email: String, password: String) =
        Credentials.emailPassword(email, password)

    suspend fun login(email: String, password: String){

        val credentials = createCredentials(email, password)
        realmApp.login(credentials)
        user = realmApp.currentUser!!
        remoteConfig()

    }

    suspend fun register(email: String, password: String){
        realmApp.emailPasswordAuth.registerUser(email, password) //register user.
        login(email, password) //login user
    }

}