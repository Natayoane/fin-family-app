package com.bandtec.finfamily

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_extract.*

class Extract : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extract)

        val total = intent.extras?.getFloat("totalFamily")

        vlTotalFamily.text = "$total"
    }
}
