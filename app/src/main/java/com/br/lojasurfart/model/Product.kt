package com.br.lojasurfart.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

class Product(
    @PrimaryKey
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("variants")
    val variants: List<ProductVariant>? = null,
    @SerializedName("deleted")
    val deleted: Boolean? = null,
    @SerializedName("rate_stars")
    val rate_stars: Int? = null
)