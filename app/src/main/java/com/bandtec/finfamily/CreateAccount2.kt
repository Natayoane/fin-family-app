package com.bandtec.finfamily

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_create_account2.*

class CreateAccount2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account2)

        buttonnext2.setOnClickListener {
            val intent = Intent(this, CreateAccount3::class.java)
            // start your next activity
            startActivity(intent)
        }
    }
}
