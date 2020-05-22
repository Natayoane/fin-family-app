package com.bandtec.finfamily

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bandtec.finfamily.popups.PopFamContribution
import kotlinx.android.synthetic.main.activity_extract.*

class Extract : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extract)

        val total = intent.extras?.getFloat("totalFamily")

        btNewContribution.setOnClickListener {
            val intent = Intent(this, PopFamContribution::class.java)
            //start your next activity
            startActivity(intent)
            finish()
        }

    }

}
