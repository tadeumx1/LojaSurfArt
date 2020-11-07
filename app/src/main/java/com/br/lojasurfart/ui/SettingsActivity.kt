package com.br.lojasurfart.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.br.lojasurfart.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : DebugActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        this.genericLayoutMenu = drawer_menu_layout
        this.genericMenuLateral = nav_view

        setupMenuDrawer()

        genericMenuLateral?.setCheckedItem(R.id.nav_config)

    }
}
