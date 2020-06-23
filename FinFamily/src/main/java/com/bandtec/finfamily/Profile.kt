package com.bandtec.finfamily

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.etEmail
import kotlinx.android.synthetic.main.activity_profile.etName
import kotlinx.android.synthetic.main.activity_profile.etNickname
import kotlinx.android.synthetic.main.activity_profile.img

class Profile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val sp: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        val btmAvatar = intent?.extras?.getParcelable<Bitmap>("avatar")

        if (btmAvatar != null) {
            img.setImageBitmap(btmAvatar)
        }

        etName.text = sp.getString("full_name", "")
        etNickname.text = sp.getString("nickname", "")
        etEmail.text = sp.getString("email", "")

        alterExpense.setOnClickListener {
            val profileEdit = Intent(this, ProfileEdit::class.java)
            profileEdit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(profileEdit)
        }

        btnLogout.setOnClickListener {
            val home = Intent(this, MainActivity::class.java)
            home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(home)
        }
    }
}

