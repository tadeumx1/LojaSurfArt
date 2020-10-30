package com.br.lojasurfart.model

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("docs")
    val docs: List<Category>
)