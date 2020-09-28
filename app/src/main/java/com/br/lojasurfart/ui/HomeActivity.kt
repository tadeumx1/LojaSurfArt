package com.br.lojasurfart.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.br.lojasurfart.R
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btnShirt.setOnClickListener {
           showProductCategoryActivity("Camisetas")
        }

        btnBermudas.setOnClickListener {
            showProductCategoryActivity("Bermudas")
        }

        btnCalcados.setOnClickListener {
            showProductCategoryActivity("Calçados")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

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
                // ação  quando terminou de buscar e enviou

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

                val intent = Intent(this, RegisterActivity::class.java)
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
                startActivity(intent)

                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showProductCategoryActivity(productCategory: String) {
        val intent = Intent(this, ProductCategoryActivity::class.java)
        intent.putExtra("productCategory", productCategory)
        startActivity(intent)
    }

    private fun showProgressBar() {
        progress_bar.visibility = View.VISIBLE
        txvHomeActivity.visibility = View.GONE

        progress_bar.postDelayed({
            progress_bar.visibility = View.GONE

            txvHomeActivity.visibility = View.VISIBLE

            Toast.makeText(this, "Lista Atualizada", Toast.LENGTH_LONG).show()
        }, 10000)
    }
}
