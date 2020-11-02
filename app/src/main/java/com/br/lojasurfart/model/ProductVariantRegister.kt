package com.br.lojasurfart.model

import com.google.gson.annotations.SerializedName

data class ProductVariantRegister(
    @SerializedName("title")
    val title: String?,
    @SerializedName("product_id")
    val productId: Int?,
    @SerializedName("size")
    val size: String?,
    @SerializedName("promotion")
    val promotion: Boolean?,
    @SerializedName("height")
    val height: Double?,
    @SerializedName("weight")
    val weight: Double?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("old_price")
    val oldPrice: Double?,
    @SerializedName("quantity")
    val quantity: Int?,
    @SerializedName("availableStock")
    val availableStock: Int?,
    @SerializedName("images")
    val images: List<String>?,
    @SerializedName("color")
    val color: ColorInformation?,
    @SerializedName("length")
    val length: Double?,
    @SerializedName("width")
    val width: Double?
)