package com.br.lojasurfart.service

import android.content.Context
import android.widget.Toast
import com.br.lojasurfart.model.*
import com.br.lojasurfart.storage.DatabaseManager
import com.br.lojasurfart.util.AndroidUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Response

object ProductService {

    var host = "https://surfart-homolog.herokuapp.com/api"
    val TAG = "WS_LojaSurfArt"


    fun getProducts(context: Context): List<ProductVariant> {
        val products: MutableList<Product>

        if (AndroidUtils.isInternetDisponivel()) {
            val url = "$host/getall/products"
            val json = HttpHelper.get(url)
            val productResponse: ProductList = parserJson(json)
            products = productResponse.docs.toMutableList()
            return transformProductVariantList(products)
        } else {
            return emptyList()
        }
    }

    fun createProduct(productRegister: ProductRegister): Product {
        if (AndroidUtils.isInternetDisponivel()) {
            val gson = Gson()

            val url = "$host/products"
            val json = HttpHelper.post(url, gson.toJson(productRegister))

            return parserJson(json)
        } else {
            return Product()
        }

    }

    fun createProductVariant(productVariantRegister: ProductVariantRegister): ProductVariant {
        if (AndroidUtils.isInternetDisponivel()) {
            val gson = Gson()

            val url = "$host/skus"
            val json = HttpHelper.post(url, gson.toJson(productVariantRegister))

            return parserJson(json)
        } else {
            return ProductVariant()
        }

    }

    private fun transformProductVariantList(products: List<Product>): List<ProductVariant> {
        val productVariantList = mutableListOf<ProductVariant>()
        if (products.isNotEmpty()) {
            products.forEach { product ->
                product.variants?.forEach { productVariant ->
                    productVariantList.add(productVariant)
                }
            }
        }

        return productVariantList
    }

    private inline fun <reified T> parserJson(json: String): T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)
    }
}