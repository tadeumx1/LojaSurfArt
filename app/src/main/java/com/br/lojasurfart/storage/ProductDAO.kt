package com.br.lojasurfart.storage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.br.lojasurfart.model.Product

@Dao
interface ProductDAO {
    @Query("SELECT * FROM product where id = :id")
    fun getById(id: Int?) : Product?

    @Query("SELECT * FROM product")
    fun findAll(): List<Product>

    @Insert
    fun insert(product: Product)

    @Delete
    fun delete(product: Product)

}