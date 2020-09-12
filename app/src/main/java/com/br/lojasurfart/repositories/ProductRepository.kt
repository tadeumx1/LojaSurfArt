package com.br.lojasurfart.repositories

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ProductRepository(private val coroutineDispatcher: CoroutineDispatcher,
                        private val productService: ProductService) {

    /**
     * Função responsável por obter os produtos da API
     */

    suspend fun getProducts() = withContext(coroutineDispatcher) {
        productService.getProducts()
    }

    suspend fun getCategories() = withContext(coroutineDispatcher) {
        productService.getCategories()
    }

}