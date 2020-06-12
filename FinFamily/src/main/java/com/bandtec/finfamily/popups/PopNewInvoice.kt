package com.bandtec.finfamily.popups

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bandtec.finfamily.R
import kotlinx.android.synthetic.main.activity_pop_new_invoice.*

class PopNewInvoice : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_new_invoice)

        val userId = intent.extras?.get("userId").toString().toInt()
        val groupId = intent.extras?.get("groupId").toString()


        btnClose.setOnClickListener {
            finish()
        }

        newEntry.setOnClickListener {
            val newEntry = Intent(this, PopNewEntry::class.java)
            newEntry.putExtra("groupId", groupId)
            newEntry.putExtra("userId", userId)
            startActivity(newEntry)
            finish()
        }

        newExit.setOnClickListener {
            val newExpense = Intent(this, PopNewExpense::class.java)
            newExpense.putExtra("groupId", groupId)
            newExpense.putExtra("userId", userId)
            startActivity(newExpense)
            finish()
        }
    }
}
