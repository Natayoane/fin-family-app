package com.bandtec.finfamily.Popups

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bandtec.finfamily.R
import kotlinx.android.synthetic.main.activity_pop_new_invoice.*

class PopNewInvoice : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_new_invoice)

        btnClose.setOnClickListener {
            finish()
        }

        newEntry.setOnClickListener {
            val intent = Intent(this, PopNewEntry::class.java)
            //start your next activity
            startActivity(intent)
            finish()
        }

        newExit.setOnClickListener {
            val intent = Intent(this, PopNewExpense::class.java)
            //start your next activity
            startActivity(intent)
            finish()
        }
    }
}
