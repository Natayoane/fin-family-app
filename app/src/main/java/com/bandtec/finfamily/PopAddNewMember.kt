package com.bandtec.finfamily

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.pop_activity_new_group.*

class PopAddNewMember : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pop_activity_add_new_member)

        btnClose.setOnClickListener {
            val intent = Intent(this, MembersGroup::class.java)
            //start your next activity
            startActivity(intent)
        }
    }
}
