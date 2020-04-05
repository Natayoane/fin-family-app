package com.bandtec.finfamily

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_panel.*
import kotlinx.android.synthetic.main.activity_pop_new_invoice.*

class Panel : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panel)

        btnProfile.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            // start your next activity
            startActivity(intent) }

        buttonpnextract.setOnClickListener {
            val intent = Intent(this, Extract::class.java)
            // start your next activity
            startActivity(intent)
        }

        bnthome.setOnClickListener {
            val intent = Intent(this, Group::class.java)
            // start your next activity
            startActivity(intent)
            finish()
        }

        btnadd.setOnClickListener {
            val intent = Intent(this, PopNewInvoice::class.java)
            // start your next activity
            startActivity(intent)
        }

        val idGrupo = 1; //Teste

        if(idGrupo == 1){
            btnProfile.setOnClickListener {
                val intent = Intent(this, MembersGroup::class.java)
                // start your next activity
                startActivity(intent) }
        }
    }
}

