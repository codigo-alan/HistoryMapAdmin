package models

import org.bson.types.ObjectId


data class MarkerEntity (
    var _id: ObjectId = ObjectId.get(),
    var name: String = "",
    var category: ObjectId = ObjectId(), //mongo relationship many to one
    var photo: String = "",
    var latitude: String = "0",
    var longitude: String = "0",
    var owner_id: String = ""
)
