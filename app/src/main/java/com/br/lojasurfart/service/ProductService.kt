package com.br.lojasurfart.service

import com.br.lojasurfart.model.Product

object ProductService {
    fun getProducts(): List<Product> {
        val products = mutableListOf<Product>()

        for (i in 1..10) {
            products.add(
                Product(
                    id = i,
                    name = "Produto $i",
                    price = i.toDouble(),
                    category = "Categoria $i",
                    description = "Descrição $i",
                    image_url = "https://source.unsplash.com/random"
                )
            )
        }

        return products
    }
}