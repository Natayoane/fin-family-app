package com.bandtec.finfamily

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_group.*

public class Group : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        val sp : SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)

        btnGroup.setOnClickListener {
            val intent = Intent(this, PopChooseGroupAction::class.java)
            //start your next activity
            startActivity(intent)
        }

    }

    fun Personal(v: View){
        btnPersonal.setOnClickListener {
            val intent = Intent(this, Panel::class.java)

            val id:Int = 0
            intent.putExtra("id", id)

            //start your next activity
            startActivity(intent)
        }
    }

    fun Group(v: View){
        val intent = Intent(this, Panel::class.java)

        val id:Int = 1
        intent.putExtra("id", id)

        startActivity(intent)
    }
}
