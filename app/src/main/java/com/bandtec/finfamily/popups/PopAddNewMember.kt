package com.bandtec.finfamily.popups

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bandtec.finfamily.MembersGroup
import com.bandtec.finfamily.R
import kotlinx.android.synthetic.main.pop_activity_new_group.*

class PopAddNewMember : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pop_activity_add_new_member)

        btnClose.setOnClickListener {
            val intent = Intent(this, MembersGroup::class.java)
            //start your next activity
            startActivity(intent)
            finish()
        }
    }
}
