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
            chooseAvatar(1)
        }

        avatar2.setOnClickListener{
            //woman_foreground
            chooseAvatar(2)
        }

        avatar3.setOnClickListener{
            //woman3_foreground
            chooseAvatar(3)
        }

        avatar4.setOnClickListener{
            //woman3_foreground
            chooseAvatar(4)
        }

        avatar5.setOnClickListener{
            //woman3_foreground
            chooseAvatar(5)
        }

        avatar6.setOnClickListener{
            //woman3_foreground
            chooseAvatar(6)
        }
    }

    private fun chooseAvatar(avatar:Int) {
        val profile = Intent(this, Profile::class.java)
        val profileEdit = Intent(this, ProfileEdit::class.java)
        val members = Intent(this, MembersGroup::class.java)
        val group = Intent(this, Group::class.java)

        profile.putExtra("avatar", avatar)
        profileEdit.putExtra("avatar", avatar)
        members.putExtra("avatar", avatar)
        group.putExtra("avatar", avatar)

        startActivity(profileEdit);
    }
}
