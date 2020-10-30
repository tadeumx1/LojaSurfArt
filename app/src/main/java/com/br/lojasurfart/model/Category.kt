package com.br.lojasurfart.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

@Entity(tableName = "category")
class Category {
    @PrimaryKey
    @SerializedName("id")
    var id: Int? = 0
    @SerializedName("name")
    var name: String? = ""
    @SerializedName("description")
    var description: String? = ""
    @SerializedName("created_at")
    var created: String? = ""
    @SerializedName("deleted")
    var deleted: Boolean? = false

    fun toJson(): String {
        return GsonBuilder().create().toJson(this)
    }
}