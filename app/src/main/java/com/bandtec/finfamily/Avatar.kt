package com.bandtec.finfamily

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_avatar.*

class Avatar : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avatar)

        avatar1.setOnClickListener{
            //man_foreground
            val avatar = 1;
            chooseAvatar(avatar)
        }

        avatar2.setOnClickListener{
            //woman_foreground
            val avatar = 2;
            chooseAvatar(avatar)
        }

        avatar3.setOnClickListener{
            //woman3_foreground
            val avatar = 3;
            chooseAvatar(avatar)
        }

        avatar4.setOnClickListener{
            //woman3_foreground
            val avatar = 4;
            chooseAvatar(avatar)
        }

        avatar5.setOnClickListener{
            //woman3_foreground
            val avatar = 5;
            chooseAvatar(avatar)
        }

        avatar6.setOnClickListener{
            //woman3_foreground
            val avatar = 6;
            chooseAvatar(avatar)
        }
    }

    fun chooseAvatar(avatar:Int) {

        val profile = Intent(this, Profile::class.java)
        val profileEdit = Intent(this, ProfileEdit::class.java)
        val members = Intent(this, MembersGroup::class.java)
        val group = Intent(this, Group::class.java)

        profile.putExtra("avatar", avatar)
        profileEdit.putExtra("avatar", avatar)
        members.putExtra("avatar", avatar)
        group.putExtra("avatar", avatar)

        startActivity(profileEdit);
        finish()
    }
}
