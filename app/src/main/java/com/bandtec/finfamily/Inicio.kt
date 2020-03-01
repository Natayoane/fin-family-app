package com.bandtec.finfamily

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_inicio.*


class Inicio : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        logininicio.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            // start your next activity
            startActivity(intent)
        }
//        cadastroinicio.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            // start your next activity
//            startActivity(intent)
//        }


    }





}
