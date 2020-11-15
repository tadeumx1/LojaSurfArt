package com.br.lojasurfart.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.br.lojasurfart.R
import com.br.lojasurfart.model.Login
import com.br.lojasurfart.util.SharedPreferencesUtil
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : DebugActivity() {

    private lateinit var sharedPreferencesUtil: SharedPreferencesUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        this.genericLayoutMenu = drawer_menu_layout
        this.genericMenuLateral = nav_view

        supportActionBar?.title = getString(R.string.configuracoes)

        sharedPreferencesUtil = SharedPreferencesUtil(this)

        setupMenuDrawer()

        txvTipoUsuario.visibility = View.GONE
        txvTipoUser.visibility = View.GONE

        genericMenuLateral?.setCheckedItem(R.id.nav_config)

        checkBoxLembrarUsuario.setOnClickListener {
            val gson = Gson()

            val userSharedPreferences = sharedPreferencesUtil.getValueString("user")
            val user = gson.fromJson<Login>(userSharedPreferences, Login::class.java)

            user.checkPassword = !user.checkPassword

            sharedPreferencesUtil.setValueString("user", gson.toJson(user))
        }

        initView()
    }

    private fun initView() {
        val gson = Gson()

        val userSharedPreferences = sharedPreferencesUtil.getValueString("user")
        val user = gson.fromJson<Login>(userSharedPreferences, Login::class.java)
        val userAdmin = sharedPreferencesUtil.getValueBoolean("permissionAdminUser")

        txvEmailUser.text = user.email

        if(userAdmin) {
            txvTipoUsuario.visibility = View.VISIBLE
            txvTipoUser.visibility = View.VISIBLE
        }

        if(user.checkPassword) {
            checkBoxLembrarUsuario.isChecked = true
        }
    }
}
