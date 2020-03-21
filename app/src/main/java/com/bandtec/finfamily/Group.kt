package com.bandtec.finfamily

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_group.*

class Group : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        imageView8.setOnClickListener {
            val intent = Intent(this, Panel::class.java)
            // start your next activity
            startActivity(intent)
        }
    }
}
