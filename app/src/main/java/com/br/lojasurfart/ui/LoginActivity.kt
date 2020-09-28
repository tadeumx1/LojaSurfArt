package com.br.lojasurfart.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.br.lojasurfart.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        btnLogin.setOnClickListener {
            val username = edtUser.text.toString()
            val password = edtPassword.text.toString()

            validateUserPassword(username, password)
        }
    }


    private fun validateUserPassword(username: String, password: String) {
        if (username == "") {
            Toast.makeText(this, "Por favor preencha o campo de usuário", Toast.LENGTH_LONG).show()
        } else if (password == "") {
            Toast.makeText(this, "Por favor preencha o campo de senha", Toast.LENGTH_LONG).show()
        } else if (username == "aluno" && password == "impacta") {
            val intent = Intent(this, HomeActivity::class.java)

            startActivity(intent)
        } else {
            Toast.makeText(this, "Usuário ou senha incorretos", Toast.LENGTH_LONG).show()
        }
    }
}
