package com.br.lojasurfart.service

import android.content.Context
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
    val TAG = "WS_LojaSurftArt"


    fun getProducts(context: Context): List<ProductVariant> {
        val products: MutableList<Product>

        if (AndroidUtils.isInternetDisponivel()) {
            val url = "$host/getall/products"
            val json = HttpHelper.get(url)
            val productResponse: ProductList = parserJson(json)
            products = productResponse.docs.toMutableList()
            // salvar offline
            for (p in products) {
                saveOffline(p)
            }
            return transformProductVariantList(products)
        } else {
            val dao = DatabaseManager.getProductDAO()
            return transformProductVariantList(dao.findAll())
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

    private fun saveOffline(product: Product) : Boolean {
        val dao = DatabaseManager.getProductDAO()

        if (!productExists(product)) {
            dao.insert(product)
        }

        return true
    }

    private fun productExists(product: Product): Boolean {
        val dao = DatabaseManager.getProductDAO()
        return dao.getById(product.id) != null
    }

    private inline fun <reified T> parserJson(json: String): T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)
    }
}