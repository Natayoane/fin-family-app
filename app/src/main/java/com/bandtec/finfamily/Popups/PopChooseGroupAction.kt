package com.bandtec.finfamily.Popups

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bandtec.finfamily.R
import kotlinx.android.synthetic.main.activity_pop_choose_group_action.*
import kotlinx.android.synthetic.main.activity_pop_choose_group_action.btnClose

class PopChooseGroupAction : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_choose_group_action)

        btnClose.setOnClickListener {
            finish()
        }

        joinGroup.setOnClickListener {
            val intent = Intent(this, PopJoinGroup::class.java)
            //start your next activity
            startActivity(intent)
            finish()
        }

        newGroup.setOnClickListener {
            val intent = Intent(this, PopNewGroup::class.java)
            //start your next activity
            startActivity(intent)
            finish()
        }
    }
}
