package com.br.lojasurfart.storage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.br.lojasurfart.model.Category

@Dao
interface CategoryDAO {
    @Query("SELECT * FROM category where id = :id")
    fun getById(id: Int?) : Category?

    @Query("SELECT * FROM category")
    fun findAll(): List<Category>

    @Insert
    fun insert(category: Category)

    @Delete
    fun delete(category: Category)

}