package com.br.lojasurfart.repositories

import com.br.lojasurfart.model.ProductResponse
import retrofit2.http.GET

interface ProductService {
    @GET("products")
    suspend fun getProducts(): ProductResponse

    @GET("categories")
    suspend fun getCategories(): ProductResponse
}