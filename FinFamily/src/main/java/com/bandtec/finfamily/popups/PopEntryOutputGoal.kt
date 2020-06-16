package com.bandtec.finfamily.popups

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bandtec.finfamily.R
import kotlinx.android.synthetic.main.activity_pop_entry_output_goal.*

class PopEntryOutputGoal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_entry_output_goal)

        btnClose.setOnClickListener {
            finish()
        }
    }
}