package com.bandtec.finfamily

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccount : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        val spCreate1 : SharedPreferences = getSharedPreferences("spCreate1", Context.MODE_PRIVATE)



        buttonnext.setOnClickListener {
            val intent = Intent(this, CreateAccount2::class.java)


            val fullName = inputname.text.toString()
            val cpf = inputcpf.text.toString()
            val birthday = inputdatebirth.text.toString()

            if (fullName.isEmpty()) {
                inputname.error = "Full Name is required!"
                inputname.requestFocus()
                return@setOnClickListener
            }

            if (cpf.isEmpty()) {
                inputcpf.error = "CPF code is required!"
                inputcpf.requestFocus()
                return@setOnClickListener
            }

            if (birthday.isEmpty()) {
                inputdatebirth.error = "Birthday is required!"
                inputdatebirth.requestFocus()
                return@setOnClickListener
            }

            spCreate1.edit().putString("full_name", fullName).apply()
            spCreate1.edit().putString("cpf", cpf).apply()
            spCreate1.edit().putString("birthday", birthday).apply()

            // start your next activity
            startActivity(intent)
        }
    }
}
