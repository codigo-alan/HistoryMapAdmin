package database


import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import models.Category
import models.MarkerEntity

class MarkerRepository(
    val markerCollection: MongoCollection<MarkerEntity>,
    val categoryCollection: MongoCollection<Category>
) {

    fun categoriesList() = categoryCollection.find().toList()

    fun addCategory(category: Category) {
        categoryCollection.insertOne(category)
    }

    fun deleteCategory(name: String){
        categoryCollection.deleteOne(Filters.eq("name", name))
    }

    fun deleteMarker(name: String) {
        markerCollection.deleteOne(Filters.eq("name", name))
    }

    fun markersList() = markerCollection.find().toList()

    //fun markersByCategory(categoryName: String) = markerCollection.find(Filters.eq("category", categoryName)).toList()

}