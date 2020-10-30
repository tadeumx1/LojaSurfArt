package com.br.lojasurfart.service

import android.content.Context
import android.widget.Toast
import com.br.lojasurfart.model.Product
import com.br.lojasurfart.model.ProductList
import com.br.lojasurfart.model.ProductRegister
import com.br.lojasurfart.model.ProductVariant
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

            /* Toast.makeText(
                context,
                "É necessário estar conectado a internet para visualizar os produtos",
                Toast.LENGTH_LONG).show() */

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