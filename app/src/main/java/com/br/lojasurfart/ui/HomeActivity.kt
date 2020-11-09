package com.br.lojasurfart.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.br.lojasurfart.R
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.widget.SearchView
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
                    recycler_view?.adapter = RecyclerAdapterProduct(this.listProduct) { onClickProduct(it) }
                }
            }

        }.start()

        progress_bar.visibility = View.GONE
    }

    private fun onClickProduct(productVariant: ProductVariant) {
        Toast.makeText(this, "Clicou produto ${productVariant.title}", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val productAddItem = menu?.findItem(R.id.action_add_item)
        if(!userAdmin) {
            productAddItem?.isVisible = false
        }

        (menu?.findItem(R.id.action_search)?.actionView as SearchView).setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                // ação enquanto está digitando

                Toast.makeText(
                    this@HomeActivity,
                    "Texto que está sendo digitado $newText",
                    Toast.LENGTH_LONG
                ).show()

                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                // ação quando terminou de buscar e enviou

                Toast.makeText(
                    this@HomeActivity,
                    "Texto após finalizar a busca $query",
                    Toast.LENGTH_LONG
                ).show()

                return false
            }

        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_search -> {
                return true
            }

            R.id.action_add_item -> {

                val intent = Intent(this, RegisterProductActivity::class.java)
                startActivity(intent)

                return true
            }

            R.id.action_update -> {

                showProgressBar()

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

    private fun showProgressBar() {
        progress_bar.visibility = View.VISIBLE
        // txvHomeActivity.visibility = View.GONE

        progress_bar.postDelayed({
            progress_bar.visibility = View.GONE

            // txvHomeActivity.visibility = View.VISIBLE

            Toast.makeText(this, "Lista Atualizada", Toast.LENGTH_LONG).show()
        }, 10000)
    }
}
