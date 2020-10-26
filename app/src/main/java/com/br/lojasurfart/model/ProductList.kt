package com.br.lojasurfart.model

import com.google.gson.annotations.SerializedName

data class ProductList(
    @SerializedName("docs")
    val docs: List<Product>
)