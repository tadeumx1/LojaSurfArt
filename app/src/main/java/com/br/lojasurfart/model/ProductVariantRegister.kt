package com.br.lojasurfart.model

import com.google.gson.annotations.SerializedName

data class ProductVariantRegister(
    @SerializedName("title")
    val title: String?,
    @SerializedName("category")
    val category: Int?,
    @SerializedName("tags")
    val tags: List<String>?
)