package com.br.lojasurfart.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.br.lojasurfart.R

class ProductCategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_category)

        val extras = intent.extras

        if (extras != null) {
            val productCategory = extras.getString("productCategory").toString()

            supportActionBar?.title = productCategory
        }
    }
}
