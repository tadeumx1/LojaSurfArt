package com.br.lojasurfart.storage

import androidx.room.Room
import com.br.lojasurfart.BaseApplication


object DatabaseManager {

    // singleton
    private var dbInstance: ProductDatabase
    init {
        val appContext = BaseApplication.getInstance().applicationContext
        dbInstance = Room.databaseBuilder(
            appContext, // contexto global
            ProductDatabase::class.java, // Referência da classe do banco
            "product.sqlite" // nome do arquivo do banco
        ).build()
    }

    fun getProductDAO(): ProductDAO {
        return dbInstance.productDAO()
    }
}