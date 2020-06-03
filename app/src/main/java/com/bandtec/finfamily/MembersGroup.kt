package com.bandtec.finfamily

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bandtec.finfamily.popups.PopAddNewMember
import kotlinx.android.synthetic.main.activity_members_group.*
import kotlinx.android.synthetic.main.fragment_members.*

class MembersGroup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_members_group)
        val sp: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)


        tvName.text = sp.getString("full_name", "Maria Antonia")
        tvNick.text = sp.getString("nickname", "Mamãe")

        btnGroup.setOnClickListener {
            val intent = Intent(this, PopAddNewMember::class.java)
            //start your next activity
            startActivity(intent)
        }
    }
}
