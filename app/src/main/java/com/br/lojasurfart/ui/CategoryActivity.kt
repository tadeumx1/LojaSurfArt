package com.br.lojasurfart.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.br.lojasurfart.R
import com.br.lojasurfart.model.Category
import com.br.lojasurfart.service.CategoryService
import com.br.lojasurfart.ui.adapter.RecyclerAdapterCategory
import com.br.lojasurfart.util.NotificationUtil
import com.br.lojasurfart.util.SharedPreferencesUtil
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.activity_category.drawer_menu_layout
import kotlinx.android.synthetic.main.activity_category.nav_view

class CategoryActivity : DebugActivity() {

    private val context: Context get() = this
    private var listCategory = listOf<Category>()
    private var userAdmin = false
    private lateinit var sharedPreferencesUtil: SharedPreferencesUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        this.genericLayoutMenu = drawer_menu_layout
        this.genericMenuLateral = nav_view

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        supportActionBar?.title = getString(R.string.categorias)

        setupMenuDrawer()

        sharedPreferencesUtil = SharedPreferencesUtil(this)

        userAdmin = sharedPreferencesUtil.getValueBoolean("permissionAdminUser")

        genericMenuLateral?.setCheckedItem(R.id.nav_categories)

        recycler_view_category?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_view_category?.itemAnimator = DefaultItemAnimator()
        recycler_view_category?.setHasFixedSize(true)

        Handler().postDelayed({
            sendNotification()
        }, 3000)
    }

    override fun onResume() {
        super.onResume()

        taskCategories()
    }

    private fun taskCategories() {
        progress_bar.visibility = View.VISIBLE

        Thread {
            this.listCategory = CategoryService.getCategories(context)
            runOnUiThread {
                // Adapter
                progress_bar.visibility = View.GONE
                recycler_view_category?.adapter = RecyclerAdapterCategory(this.listCategory) { onClickCategory(it) }

            }
        }.start()

    }

    private fun sendNotification() {
        val intent = Intent(this, HomeActivity::class.java)

        NotificationUtil.create(this, 1, intent, "LojaSurfArt", "Confira as novas promoções")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val categoryAddItem = menu?.findItem(R.id.action_add_item)
        if(!userAdmin) {
            categoryAddItem?.isVisible = false
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.action_add_item -> {

                val intent = Intent(this, RegisterCategoryActivity::class.java)
                startActivity(intent)

                return true
            }

            R.id.action_update -> {

                taskCategories()
                (recycler_view_category?.adapter as RecyclerAdapterCategory).notifyDataSetChanged()

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

    private fun onClickCategory(category: Category) {
        // Toast.makeText(this, "Clicou categoria ${category.name}", Toast.LENGTH_SHORT).show()
    }
}
