package com.bandtec.finfamily.popups

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bandtec.finfamily.Extract
import com.bandtec.finfamily.R
import kotlinx.android.synthetic.main.activity_pop_new_invoice.*

class PopFamContribution : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_fam_contribution)

        btnClose.setOnClickListener {
            val intent = Intent(this, Extract::class.java)
            //start your next activity
            startActivity(intent)
            finish()
        }
    }
}
