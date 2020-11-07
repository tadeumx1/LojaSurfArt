package com.br.lojasurfart.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.br.lojasurfart.R
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : DebugActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        this.genericLayoutMenu = drawer_menu_layout
        this.genericMenuLateral = nav_view

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        supportActionBar?.title = "Localização"

        setupMenuDrawer()

        genericMenuLateral?.setCheckedItem(R.id.nav_location)

    }

    override fun onResume() {
        super.onResume()
        val mapaFragment = MapFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.layout_map, mapaFragment)
            .commit()
    }
}
