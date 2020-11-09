package com.br.lojasurfart.service

import android.content.Context
import com.br.lojasurfart.model.Category
import com.br.lojasurfart.model.Login
import com.br.lojasurfart.model.LoginResponse
import com.br.lojasurfart.storage.DatabaseManager
import com.br.lojasurfart.util.AndroidUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Response

object LoginService {

    var host = "https://surfart-homolog.herokuapp.com/api"
    val TAG = "WS_LojaSurfArt"

    fun loginUser(context: Context, loginModel: Login): LoginResponse {
        if (AndroidUtils.isInternetDisponivel()) {
            val gson = Gson()

            val url = "$host/customers/auth"
            val json = HttpHelper.post(url, gson.toJson(loginModel))

            return parserJson(json)
        } else {
            return LoginResponse(name = "", token = "", admin = false)
        }

    }

    private inline fun <reified T> parserJson(json: String): T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)
    }
}