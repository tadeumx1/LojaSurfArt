package com.br.lojasurfart.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.br.lojasurfart.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.toolbar.*

open class DebugActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var genericLayoutMenu: DrawerLayout? = null
    var genericMenuLateral: NavigationView? = null

    protected fun setupMenuDrawer() {
        val toggle = ActionBarDrawerToggle(
            this,
            genericLayoutMenu,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        genericLayoutMenu?.addDrawerListener(toggle)
        toggle.syncState()

        genericMenuLateral?.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_products -> {
                Toast.makeText(this, "Clicou Produtos", Toast.LENGTH_SHORT).show()

                if(this@DebugActivity is HomeActivity) {
                   // You are in the Same Page"

                } else {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }
            }
            R.id.nav_categories -> {
                val intent = Intent(this, CategoryActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_location -> {
                Toast.makeText(this, "Clicou Localização", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_config -> {
                Toast.makeText(this, "Clicou Configuração", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_quit_user -> {
                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }

        genericLayoutMenu?.closeDrawer(GravityCompat.START)
        return true
    }
}
