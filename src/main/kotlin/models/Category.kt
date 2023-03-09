package models

import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class Category(
    @PrimaryKey
    var _id: ObjectId = ObjectId.create(),
    var name: String = "",
    var owner_id: String = ""
) : RealmObject {
    constructor() : this(owner_id = "") {}

    override fun toString(): String {
        return "Category($_id, $name)"
    }
}
