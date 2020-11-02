package com.br.lojasurfart.model

import com.google.gson.annotations.SerializedName

data class ProductVariant(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("quantity")
    val quantity: Int? = null,
    @SerializedName("images")
    val images: List<String>? = null,
    @SerializedName("price")
    val price: Double? = null,
    @SerializedName("old_price")
    val oldPrice: Double? = null
)