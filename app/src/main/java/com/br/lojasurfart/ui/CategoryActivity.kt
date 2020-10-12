package com.br.lojasurfart.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.br.lojasurfart.R
import kotlinx.android.synthetic.main.activity_category.*

class CategoryActivity : DebugActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        this.genericLayoutMenu = drawer_menu_layout
        this.genericMenuLateral = nav_view

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        setupMenuDrawer()
    }
}
