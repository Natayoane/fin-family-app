package com.bandtec.finfamily.popups

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bandtec.finfamily.Group
import com.bandtec.finfamily.R
import com.bandtec.finfamily.api.RetrofitClient
import kotlinx.android.synthetic.main.activity_pop_delete_put.*
import kotlinx.android.synthetic.main.activity_pop_new_invoice.btnClose
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopDeletePut : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_delete_put)

        val transId = intent?.extras?.getInt("id", 0)

        btnClose.setOnClickListener {
            finish()
        }

        btNo.setOnClickListener {
            finish()
        }

        btYes.setOnClickListener {
            val groups = Intent(this, Group::class.java)
            deleteExpense(transId!!)
            groups.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(groups)
            finish()
        }
    }

    fun deleteExpense(transId: Int) {
        RetrofitClient.instance.removeTransactions(transId)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.code().toString() == "200") {
                        Toast.makeText(applicationContext, "Transação removida com sucesso!", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Houve um erro ao remover a transação!\nTente novamente mais tarde!",
                            Toast.LENGTH_LONG
                        ).show()
                        println("Something are wrong!")
                    }


                }

            })
    }
}
