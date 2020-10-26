package com.br.lojasurfart.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.br.lojasurfart.R
import com.br.lojasurfart.model.Product
import com.br.lojasurfart.model.ProductVariant
import com.br.lojasurfart.service.ProductService
import com.br.lojasurfart.ui.adapter.RecyclerAdapterProduct
import kotlinx.android.synthetic.main.activity_product.*

class ProductActivity : DebugActivity() {

    private var listProduct = listOf<ProductVariant>()

    // private var listProductVariant = listOf<ProductVariant>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterProduct: RecyclerAdapterProduct
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        this.genericLayoutMenu = drawer_menu_layout
        this.genericMenuLateral = nav_view

        setupMenuDrawer()

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recyclerView = recycler_view

        layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)

        recyclerView.layoutManager = layoutManager

        recyclerView.setHasFixedSize(true)

        Thread {
            this.listProduct = ProductService.getProducts(this)
            runOnUiThread {

                // Adapter
                adapterProduct = RecyclerAdapterProduct(listProduct) {
                    onClickProduct(it)
                }

                recyclerView.adapter = adapterProduct

            }
        }.start()

    }

    private fun transformProductVariantList(products: List<Product>): List<ProductVariant> {
        val productVariantList = mutableListOf<ProductVariant>()
        if (products.isNotEmpty()) {
            products.forEach { product ->
                product.variants?.forEach { productVariant ->
                    productVariantList.add(productVariant)
                }
            }
        }

        return productVariantList
    }

    private fun onClickProduct(productVariant: ProductVariant) {
        Toast.makeText(this, "Clicou produto ${productVariant.title}", Toast.LENGTH_SHORT).show()
    }
}
