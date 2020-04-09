package com.bandtec.finfamily

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_panel.*
import kotlinx.android.synthetic.main.activity_pop_new_invoice.*

class Panel : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panel)
        val sp: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)

        val id = intent.extras?.getInt("id");

        if(id!! == 0 ) btnProfile.setImageDrawable(getDrawable(R.drawable.ic_person)) else btnProfile.setImageDrawable(getDrawable(R.drawable.ic_people))

        if (id == 0) {
            btnProfile.setOnClickListener {
                val intent = Intent(this, Profile::class.java)
                startActivity(intent)
            }
        } else {
            btnProfile.setOnClickListener {
                val intent = Intent(this, MembersGroup::class.java)
                startActivity(intent)
            }
        }

        buttonpnextract.setOnClickListener {
            val intent = Intent(this, Extract::class.java)
            // start your next activity
            startActivity(intent)
        }

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

        if (idGrupo == 1) {
            btnProfile.setOnClickListener {

                val intent = Intent(this, Profile::class.java)
                // start your next activity
                startActivity(intent)
            }
        } else if (idGrupo == 2) {
            btnProfile.setOnClickListener {
            val intent = Intent(this, MembersGroup::class.java)
            // start your next activity
            startActivity(intent)
            }
        }
    }
}

