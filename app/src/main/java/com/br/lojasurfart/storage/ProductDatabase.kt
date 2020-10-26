package com.br.lojasurfart.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.br.lojasurfart.model.Product


// anotação define a lista de entidades e a versão do banco
@Database(entities = [Product::class], version = 1, exportSchema = false)
@TypeConverters(ProductVariantListConverter::class)
abstract class ProductDatabase: RoomDatabase() {
    abstract fun productDAO(): ProductDAO
}