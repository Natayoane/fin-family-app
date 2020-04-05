package com.bandtec.finfamily

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.pop_activity_new_group.*

class PopJoinGroup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_join_group)

        btnClose.setOnClickListener {
            val intent = Intent(this, PopChooseGroupAction::class.java)
            //start your next activity
            startActivity(intent)
            finish()
        }
    }
}
