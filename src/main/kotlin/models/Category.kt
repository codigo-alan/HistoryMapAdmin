package models

import org.bson.types.ObjectId

data class Category(
    var _id: ObjectId = ObjectId.get(),
    var name: String = "",
    var owner_id: String = ""
)
