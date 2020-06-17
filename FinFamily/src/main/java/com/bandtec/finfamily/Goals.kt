package com.bandtec.finfamily

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bandtec.finfamily.popups.PopNewGoal
import kotlinx.android.synthetic.main.activity_goals.*

class Goals : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goals)

        btnNewGoal.setOnClickListener {
            var intent =  Intent(this, PopNewGoal::class.java)
            startActivity(intent)
        }
    }
}