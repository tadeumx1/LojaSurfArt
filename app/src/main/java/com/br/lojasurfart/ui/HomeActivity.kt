package com.br.lojasurfart.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.br.lojasurfart.R
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.br.lojasurfart.model.ProductVariant
import com.br.lojasurfart.service.ProductService
import com.br.lojasurfart.ui.adapter.RecyclerAdapterProduct
import com.br.lojasurfart.util.SharedPreferencesUtil
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : DebugActivity() {

    private val context: Context get() = this
    private var listProduct = listOf<ProductVariant>()
    private var userAdmin = false
    private lateinit var sharedPreferencesUtil: SharedPreferencesUtil

    // private var listProductVariant = listOf<ProductVariant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        this.genericLayoutMenu = drawer_menu_layout
        this.genericMenuLateral = nav_view

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        setupMenuDrawer()

        sharedPreferencesUtil = SharedPreferencesUtil(this)

        userAdmin = sharedPreferencesUtil.getValueBoolean("permissionAdminUser")

        genericMenuLateral?.setCheckedItem(R.id.nav_products)

        recycler_view?.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        recycler_view?.itemAnimator = DefaultItemAnimator()
        recycler_view?.setHasFixedSize(true)

    }

    override fun onResume() {
        super.onResume()

        taskProducts()
    }

    private fun taskProducts() {
        progress_bar.visibility = View.VISIBLE

        Thread {

            this.listProduct = ProductService.getProducts(context)

            if(listProduct.isEmpty()) {
                Toast.makeText(this, "Erro ao carregar produtos", Toast.LENGTH_LONG).show()
            } else {
                runOnUiThread {
                    // Adapter
                    progress_bar.visibility = View.GONE
                    recycler_view?.adapter = RecyclerAdapterProduct(this.listProduct) { onClickProduct(it) }
                }
            }

        }.start()
    }

    private fun onClickProduct(productVariant: ProductVariant) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://surfartbrazil.herokuapp.com/produtos/detalhes/" + productVariant.productId))
        startActivity(browserIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val productAddItem = menu?.findItem(R.id.action_add_item)
        if(!userAdmin) {
            productAddItem?.isVisible = false
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.action_add_item -> {

                val intent = Intent(this, RegisterProductActivity::class.java)
                startActivity(intent)

                return true
            }

            R.id.action_update -> {

                taskProducts()
                (recycler_view?.adapter as RecyclerAdapterProduct).notifyDataSetChanged()

                return true
            }

            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)

                return true
            }

            R.id.action_quit_user -> {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
