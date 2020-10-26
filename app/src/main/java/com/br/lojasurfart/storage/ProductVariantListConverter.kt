package com.br.lojasurfart.storage

import androidx.room.TypeConverter
import com.br.lojasurfart.model.ProductVariant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ProductVariantListConverter {

    @TypeConverter
    fun fromProductVariantList(productVariantList: List<ProductVariant>?): String? {
        if (productVariantList == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<ProductVariant>>() {

        }.getType()
        return gson.toJson(productVariantList, type)
    }

    @TypeConverter
    fun toProductVariantList(productVariantString: String?): List<ProductVariant>? {
        if (productVariantString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<ProductVariant>>() {

        }.getType()
        return gson.fromJson<List<ProductVariant>>(productVariantString, type)
    }
}