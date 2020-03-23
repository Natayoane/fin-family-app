package com.bandtec.finfamily

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create_account2.*

class CreateAccount2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account2)

        val spCreate2 : SharedPreferences = getSharedPreferences("spCreate1", Context.MODE_PRIVATE)

        buttonnext2.setOnClickListener {
            val intent = Intent(this, CreateAccount3::class.java)

            val email = inputcadastroemail.text.toString()
            val password = inputpassword.text.toString()
            val passwordConfirm = inputverificapassword.text.toString()

            if (email.isEmpty()) {
                inputcadastroemail.error = "Email is required!"
                inputcadastroemail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                inputpassword.error = "Password code is required!"
                inputpassword.requestFocus()
                return@setOnClickListener
            }

            if (passwordConfirm.isEmpty()) {
                inputverificapassword.error = "Password confirmation is required!"
                inputverificapassword.requestFocus()
                return@setOnClickListener
            }

            if (password != passwordConfirm) {
                inputpassword.error = "Passwords must be the same!"
                inputverificapassword.error = "Passwords must be the same!"
                return@setOnClickListener
            }

            spCreate2.edit().putString("email", email).apply()
            spCreate2.edit().putString("password", password).apply()

            startActivity(intent)
        }
    }
}
