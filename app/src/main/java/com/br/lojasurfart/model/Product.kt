package com.br.lojasurfart.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "product")
class Product(
    @PrimaryKey
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("variants")
    val variants: List<ProductVariant>?,
    @SerializedName("deleted")
    val deleted: Boolean?,
    @SerializedName("rate_stars")
    val rate_stars: Int?
)