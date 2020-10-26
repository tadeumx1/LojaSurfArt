package com.br.lojasurfart.model

import com.google.gson.annotations.SerializedName

data class ProductVariant(
    @SerializedName("title")
    val title: String?,
    @SerializedName("quantity")
    val quantity: Int?,
    @SerializedName("images")
    val images: List<String>?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("old_price")
    val oldPrice: Double?
)