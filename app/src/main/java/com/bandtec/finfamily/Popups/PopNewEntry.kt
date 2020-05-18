package com.bandtec.finfamily.Popups

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bandtec.finfamily.Panel
import com.bandtec.finfamily.R
import kotlinx.android.synthetic.main.activity_pop_new_expense.*
import kotlinx.android.synthetic.main.activity_pop_new_invoice.btnClose

class PopNewEntry : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_new_entry)

        btnClose.setOnClickListener {
            val intent = Intent(this, PopNewInvoice::class.java)
            //start your next activity
            startActivity(intent)
            finish()
        }
    }

    fun newEntry(v: View){
        val value = value.text.toString().toFloat()
        val intent = Intent(this, Panel::class.java)
        intent.putExtra("entry",value)

        startActivity(intent)
        finish()
    }
}
