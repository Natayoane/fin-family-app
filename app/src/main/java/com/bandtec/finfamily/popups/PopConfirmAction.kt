package com.bandtec.finfamily.popups

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bandtec.finfamily.R
import kotlinx.android.synthetic.main.activity_pop_delete_put.*
import kotlinx.android.synthetic.main.activity_pop_delete_put.btnClose

class PopConfirmAction : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_confirm_action)

        val choose = intent.extras?.getInt("choose")

        if (choose == 0)  txtTitle.text = "Confirm delete account"
        if (choose == 1)  txtTitle.text = "Are you sure you want to leave ?"

        btnClose.setOnClickListener {
            finish()
        }
    }
}
