package com.br.lojasurfart.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.br.lojasurfart.R
import kotlinx.android.synthetic.main.activity_product_category.*

class ProductCategoryActivity : DebugActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_category)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        this.genericLayoutMenu = drawer_menu_layout
        this.genericMenuLateral = nav_view

        val extras = intent.extras

        if (extras != null) {
            val productCategory = extras.getString("productCategory").toString()

            supportActionBar?.title = productCategory
        }

        setupMenuDrawer()
    }
}
