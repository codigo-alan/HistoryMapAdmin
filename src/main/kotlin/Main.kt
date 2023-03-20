import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import database.MarkerRepository
import models.Category
import models.MarkerEntity
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.pojo.PojoCodecProvider
import ui.Ui
import java.util.*


/**
 * Instantiate MarkerRepository, Ui
 */
val scanner = Scanner(System.`in`)
val ui = Ui(scanner)
fun main() {

    //Client
    val pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build()
    val pojoCodecRegistry = CodecRegistries.fromRegistries(
        MongoClientSettings.getDefaultCodecRegistry(),
        CodecRegistries.fromProviders(pojoCodecProvider)
    )
    val connectionString =
        ConnectionString("mongodb+srv://alan:ITB2021316@atlascluster.c3venvw.mongodb.net/?retryWrites=true&w=majority")
    val settings: MongoClientSettings = MongoClientSettings.builder()
        .applyConnectionString(connectionString)
        .serverApi(
            ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build()
        )
        .build()
    val mongoClient: MongoClient = MongoClients.create(settings)

    //Collections
    val database: MongoDatabase = mongoClient.getDatabase("todo")
        .withCodecRegistry(pojoCodecRegistry);
    val markersCollection: MongoCollection<MarkerEntity> = database.getCollection("MarkerEntity", MarkerEntity::class.java)
    val categoryCollections: MongoCollection<Category> = database.getCollection("Category", Category::class.java)

    val markerRepository = MarkerRepository(markersCollection, categoryCollections)
    /**
     * User keyboard selects
     */
    var userDashboardSelect = ui.userDashboard()

    while(true) {
        when (userDashboardSelect) {
            1 -> getAllMarkers(markerRepository)
            2 -> getAllCategories(markerRepository)
            3 -> filterMarkerByCategory(markerRepository)
            4 -> addCategory(markerRepository)
            5 -> deleteMarker(markerRepository)
            6 -> deleteCategory(markerRepository)
            0 -> break
            else -> ui.notValid()
        }
        userDashboardSelect = ui.userDashboard()
    }


}

fun deleteCategory(markerRepository: MarkerRepository) {
    println("Ingrese nombre de categoría que desa borrar: ")
    val catName = scanner.nextLine()
    markerRepository.deleteCategory(catName)
}

fun addCategory(markerRepository: MarkerRepository) {
    println("Ingrese nombre de categoría: ")
    val catName = scanner.nextLine()
    val category = Category(name = catName)
    markerRepository.addCategory(category)
}

fun getAllCategories(markerRepository: MarkerRepository) {
    val listOfCategories = markerRepository.categoriesList()
    ui.showListOfCategories(listOfCategories)
}

fun deleteMarker(markerRepository: MarkerRepository) {
    println("Ingrese nombre de Marker que desea borrar: ")
    val markerName = scanner.nextLine()
    markerRepository.deleteMarker(markerName)
}

fun filterMarkerByCategory(markerRepository: MarkerRepository) {
    /*val categoryName = scanner.nextLine()
    val listOfMarkers = markerRepository.markersByCategory(categoryName)
    ui.showListOfMarkers(listOfMarkers)*/
    println("Not implemented yet")
}

fun getAllMarkers(markerRepository: MarkerRepository) {
    val listOfMarkers = markerRepository.markersList()
    ui.showListOfMarkers(listOfMarkers)
}
