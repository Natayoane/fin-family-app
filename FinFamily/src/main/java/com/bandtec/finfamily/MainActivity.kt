package com.bandtec.finfamily

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logininicio.setOnClickListener {
            val signin = Intent(this, Login::class.java)
            startActivity(signin)
        }
        cadastroinicio.setOnClickListener {
            val signup = Intent(this, CreateAccount::class.java)
            startActivity(signup)
        }
    }
}
