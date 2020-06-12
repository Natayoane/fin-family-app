package com.bandtec.finfamily.popups

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bandtec.finfamily.MainActivity
import com.bandtec.finfamily.R
import com.bandtec.finfamily.api.RetrofitClient
import kotlinx.android.synthetic.main.activity_pop_confirm_action.*
import kotlinx.android.synthetic.main.activity_pop_delete_put.*
import kotlinx.android.synthetic.main.activity_pop_delete_put.btnClose
import kotlinx.android.synthetic.main.activity_pop_delete_put.txtTitle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopConfirmAction : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_confirm_action)
        val user = getSharedPreferences("user", Context.MODE_PRIVATE)

        val choose = intent.extras?.getInt("choose")

        if (choose == 0) txtTitle.text = "Confirm delete account"
        if (choose == 1) txtTitle.text = "Are you sure you want to leave ?"

        btnClose.setOnClickListener {
            finish()
        }

        btNo.setOnClickListener {
            finish()
        }

        btYes.setOnClickListener {
            val home = Intent(this, MainActivity::class.java)
            removeUser(user.getInt("userId", 0))
            startActivity(home)

        }
    }

    fun removeUser(userId: Int) {
        RetrofitClient.instance.removeUser(userId)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    when {
                        response.code().toString() == "200" -> {
                            Toast.makeText(
                                applicationContext,
                                "Usuário removido com sucesso!",
                                Toast.LENGTH_LONG
                            ).show()

                        }
                        else -> {
                            Toast.makeText(
                                applicationContext,
                                "Houve um erro! Por favor, tente novamente mais tarde.",
                                Toast.LENGTH_LONG
                            ).show()

                        }
                    }
                }
            })
    }
}
