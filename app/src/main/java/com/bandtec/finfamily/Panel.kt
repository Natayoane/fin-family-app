package com.bandtec.finfamily

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_panel.*

class Panel : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panel)

        buttonpnextract.setOnClickListener {
            val intent = Intent(this, Extract::class.java)
            // start your next activity
            startActivity(intent)
        }

        imageView7.setOnClickListener {
            val intent = Intent(this, Extract::class.java)
            // start your next activity
            startActivity(intent)
        }

        imageView5.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            // start your next activity
            startActivity(intent)
        }

        imageView6.setOnClickListener {
            val intent = Intent(this, MembersGroup::class.java)
            // start your next activity
            startActivity(intent)
        }

        imageView3.setOnClickListener {
            val intent = Intent(this, Group::class.java)
            // start your next activity
            startActivity(intent)
        }
    }
}
