package com.br.lojasurfart.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.br.lojasurfart.R
import com.br.lojasurfart.model.Category
import com.br.lojasurfart.service.CategoryService
import com.br.lojasurfart.ui.adapter.RecyclerAdapterCategory
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.activity_category.drawer_menu_layout
import kotlinx.android.synthetic.main.activity_category.nav_view

class CategoryActivity : DebugActivity() {

    private val context: Context get() = this
    private var listCategory = listOf<Category>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        this.genericLayoutMenu = drawer_menu_layout
        this.genericMenuLateral = nav_view

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        setupMenuDrawer()

        recycler_view_category?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_view_category?.itemAnimator = DefaultItemAnimator()
        recycler_view_category?.setHasFixedSize(true)
    }

    override fun onResume() {
        super.onResume()

        taskCategories()
    }

    private fun taskCategories() {
        Thread {
            this.listCategory = CategoryService.getCategories(context)
            runOnUiThread {
                // Adapter
                recycler_view_category?.adapter = RecyclerAdapterCategory(this.listCategory) { onClickCategory(it) }

            }
        }.start()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.action_add_item -> {

                val intent = Intent(this, RegisterCategoryActivity::class.java)
                startActivity(intent)

                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onClickCategory(category: Category) {
        Toast.makeText(this, "Clicou categoria ${category.name}", Toast.LENGTH_SHORT).show()
    }
}
