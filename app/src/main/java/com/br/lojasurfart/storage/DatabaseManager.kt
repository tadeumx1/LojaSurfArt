package com.br.lojasurfart.storage

import androidx.room.Room
import com.br.lojasurfart.BaseApplication


object DatabaseManager {

    // singleton
    private var dbInstance: CategoryDatabase
    init {
        val appContext = BaseApplication.getInstance().applicationContext
        dbInstance = Room.databaseBuilder(
            appContext, // contexto global
            CategoryDatabase::class.java, // Referência da classe do banco
            "category.sqlite" // nome do arquivo do banco
        ).build()
    }

    fun getCategoryDAO(): CategoryDAO {
        return dbInstance.categoryDAO()
    }
}