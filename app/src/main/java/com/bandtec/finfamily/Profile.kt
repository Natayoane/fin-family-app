package com.bandtec.finfamily

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_profile.*


class Profile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val sp: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)


        etName.text = sp.getString("full_name", "Maria Antonia")
        etNickname.text = sp.getString("nickname", "Mamãe")
        etEmail.text = sp.getString("email", "mariaantonia@gmail.com")


        settingsProfile.setOnClickListener {
            val intent = Intent(this, ProfileEdit::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // start your next activity
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("choose", 1)
            // start your next activity
            startActivity(intent)
        }
    }
}

