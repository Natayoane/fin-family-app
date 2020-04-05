package com.bandtec.finfamily

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_group.*

public class Group : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        val sp : SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)

        individual.setOnClickListener {
           val intent = Intent(this, Panel::class.java)
            //start your next activity
            startActivity(intent)
        }

        btnGroup.setOnClickListener {
            val intent = Intent(this, PopChooseGroupAction::class.java)
            //start your next activity
            startActivity(intent)
        }
    }
}
