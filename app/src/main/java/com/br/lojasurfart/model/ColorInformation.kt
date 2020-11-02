package com.br.lojasurfart.model

import com.google.gson.annotations.SerializedName

data class ColorInformation(
    @SerializedName("title")
    val title: String,
    @SerializedName("code")
    val code: String
)