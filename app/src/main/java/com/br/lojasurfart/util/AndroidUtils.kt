package com.br.lojasurfart.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.br.lojasurfart.BaseApplication

object AndroidUtils {
    fun isInternetDisponivel(): Boolean {
        val conexao =
            BaseApplication.getInstance().applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val redes = conexao.allNetworks
        return redes.map { conexao.getNetworkInfo(it) }
            .any { it.state == NetworkInfo.State.CONNECTED }
    }
}