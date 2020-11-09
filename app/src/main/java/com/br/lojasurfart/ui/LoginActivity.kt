package com.br.lojasurfart.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.br.lojasurfart.R
import com.br.lojasurfart.model.Category
import com.br.lojasurfart.model.Login
import com.br.lojasurfart.model.LoginResponse
import com.br.lojasurfart.service.CategoryService
import com.br.lojasurfart.service.LoginService
import com.br.lojasurfart.util.SharedPreferencesUtil
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val context: Context get() = this
    private lateinit var loginResponse: LoginResponse
    private lateinit var sharedPreferencesUtil: SharedPreferencesUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        sharedPreferencesUtil = SharedPreferencesUtil(this)

        val gson = Gson()

        val userSharedPreferences = sharedPreferencesUtil.getValueString("user")
        val user = gson.fromJson<Login>(userSharedPreferences, Login::class.java)

        if(user != null) {
            edtUser.setText(user.email)
            edtPassword.setText(user.password)
            checkBoxPassword.isChecked = true
        }

        btnLogin.setOnClickListener {
            val username = edtUser.text.toString()
            val password = edtPassword.text.toString()

            // validateUserPassword(username, password)
            validateUser(username, password)
        }
    }

    private fun validateUser(username: String, password: String) {
        if (username == "") {
            Toast.makeText(this, "Por favor preencha o campo de usuário", Toast.LENGTH_LONG).show()
        } else if (password == "") {
            Toast.makeText(this, "Por favor preencha o campo de senha", Toast.LENGTH_LONG).show()
        } else {

            val login = Login(
                email = username,
                password = password
            )

            btnLogin.visibility = View.GONE
            progress_bar.visibility = View.VISIBLE

            Thread {
                loginResponse = LoginService.loginUser(context, login)
                runOnUiThread {

                    if(loginResponse.name != "" && loginResponse.token != "") {
                        saveUserChecked(login)

                        sharedPreferencesUtil.setValueBoolean("permissionAdminUser", loginResponse.admin)

                        val intent = Intent(this, HomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()

                        progress_bar.visibility = View.GONE
                        btnLogin.visibility = View.VISIBLE
                    } else {
                        Toast.makeText(this, "Usuário ou senha incorretos", Toast.LENGTH_LONG).show()

                        progress_bar.visibility = View.GONE
                        btnLogin.visibility = View.VISIBLE
                    }
                }
            }.start()
        }
    }

    private fun saveUserChecked(login: Login) {

        val gson = Gson()

        if(checkBoxPassword.isChecked) {
            sharedPreferencesUtil.setValueString("user", gson.toJson(login))
        }
    }

}
