package com.br.lojasurfart.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.br.lojasurfart.R
import com.br.lojasurfart.model.Category
import com.br.lojasurfart.service.CategoryService
import kotlinx.android.synthetic.main.activity_register_category.*
import kotlinx.android.synthetic.main.activity_register_category.drawer_menu_layout
import kotlinx.android.synthetic.main.activity_register_category.nav_view

class RegisterCategoryActivity : DebugActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_category)

        this.genericLayoutMenu = drawer_menu_layout
        this.genericMenuLateral = nav_view

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        setupMenuDrawer()

        setupListener()
    }

    private fun setupListener() {
        btnSendProduct.setOnClickListener {
            registerCategory()
        }
    }

    private fun registerCategory() {
        if (edtCategoryTitle.text.toString() != "" && edtCategoryDescription.text.toString() != "") {
            val categoryTitle = edtCategoryTitle.text.toString()
            val categoryDescription = edtCategoryDescription.text.toString()

            val category = Category()
            category.name = categoryTitle
            category.description = categoryDescription

            taskRegisterCategory(category)

        } else {
            Toast.makeText(this, "Preencha os campos", Toast.LENGTH_LONG).show()
        }
    }

    private fun taskRegisterCategory(category: Category) {
        Thread {
            CategoryService.createCategory(category)
            runOnUiThread {
                // após cadastrar, voltar para activity anterior
                Toast.makeText(this, "Categoria cadastrada", Toast.LENGTH_LONG).show()
                finish()
            }
        }.start()
    }

}
