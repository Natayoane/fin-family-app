package com.bandtec.finfamily

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_pop_new_invoice.*

class PopNewExpense : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_new_expense)

        btnClose.setOnClickListener {
            val intent = Intent(this, PopNewInvoice::class.java)
            //start your next activity
            startActivity(intent)
            finish()
        }

    }
}
