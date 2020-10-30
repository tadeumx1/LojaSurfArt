package com.br.lojasurfart.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.br.lojasurfart.model.Category
import com.br.lojasurfart.model.Product


// anotação define a lista de entidades e a versão do banco
@Database(entities = [Category::class], version = 1, exportSchema = false)
abstract class CategoryDatabase: RoomDatabase() {
    abstract fun categoryDAO(): CategoryDAO
}