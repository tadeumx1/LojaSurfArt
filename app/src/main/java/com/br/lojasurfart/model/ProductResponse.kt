package com.br.lojasurfart.model

import com.google.gson.annotations.SerializedName

data class ProductResponse (
    @SerializedName("docs") val docs: List<Product>,
    @SerializedName("total") val total: Int?,
    @SerializedName("limit") val limit: Int?,
    @SerializedName("page") val page: Int?,
    @SerializedName("pages") val pages: Int?
)