package com.bandtec.finfamily

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_modal_entry.*
import kotlinx.android.synthetic.main.activity_pop_new_invoice.*
import kotlinx.android.synthetic.main.activity_pop_new_invoice.btnClose

class ModalEntry : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modal_entry)

        btnClose.setOnClickListener {
            val intent = Intent(this, Extract::class.java)
            //start your next activity
            startActivity(intent)
            finish()
        }
    }

    fun alterPut(v:View){
        val intent = Intent(this, PopAlterPut::class.java)
        //start your next activity
        startActivity(intent)
        finish()
    }
}
