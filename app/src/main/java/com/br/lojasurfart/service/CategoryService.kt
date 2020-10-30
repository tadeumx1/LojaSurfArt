package com.br.lojasurfart.service

import android.content.Context
import com.br.lojasurfart.model.Category
import com.br.lojasurfart.model.CategoryResponse
import com.br.lojasurfart.storage.DatabaseManager
import com.br.lojasurfart.util.AndroidUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Response

object CategoryService {

    var host = "https://surfart-homolog.herokuapp.com/api"
    val TAG = "WS_LojaSurfArt"

    fun getCategories(context: Context): List<Category> {
        val categories: ArrayList<Category>
        if (AndroidUtils.isInternetDisponivel()) {
            val url = "$host/getall/categories"
            val json = HttpHelper.get(url)
            val categoryResponse: CategoryResponse = parserJson(json)
            val categories = categoryResponse.docs
            // salvar offline
            for (c in categories) {
                saveOffline(c)
            }
            return categories
        } else {
            val dao = DatabaseManager.getCategoryDAO()
            return dao.findAll()
        }

    }

    fun createCategory(category: Category): Response {
        if (AndroidUtils.isInternetDisponivel()) {
            val json = HttpHelper.post("$host/categories", category.toJson())
            return parserJson(json)
        }
        else {
            saveOffline(category)
            val builder = Response.Builder()
            builder.code(200)
            builder.message("Categoria salva no dispositivo")
            return builder.build()
        }
    }

    private fun saveOffline(category: Category) : Boolean {
        val dao = DatabaseManager.getCategoryDAO()

        if (!categoryExists(category)) {
            dao.insert(category)
        }

        return true
    }

    private fun categoryExists(category: Category): Boolean {
        val dao = DatabaseManager.getCategoryDAO()
        return dao.getById(category.id) != null
    }

    fun delete(category: Category): Response {
        if (AndroidUtils.isInternetDisponivel()) {
            val url = "$host/categories/${category.id}"
            val json = HttpHelper.delete(url)

            return parserJson(json)
        } else {
            val dao = DatabaseManager.getCategoryDAO()
            dao.delete(category)

            val builder = Response.Builder()
            builder.code(200)
            builder.message("Dados salvos localmente")
            return builder.build()
        }

    }

    private inline fun <reified T> parserJson(json: String): T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)
    }
}