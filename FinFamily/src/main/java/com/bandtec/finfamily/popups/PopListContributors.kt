package com.bandtec.finfamily.popups

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bandtec.finfamily.R
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.model.GroupResponse
import com.bandtec.finfamily.model.UserResponse
import kotlinx.android.synthetic.main.activity_group.*
import kotlinx.android.synthetic.main.activity_pop_list_contributors.*
import kotlinx.android.synthetic.main.fragment_contributors.*
import kotlinx.android.synthetic.main.fragment_contributors.nome
import kotlinx.android.synthetic.main.fragment_contributors.valor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopListContributors : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_list_contributors)

        val value = intent.extras?.get("value").toString()
        val userId = intent.extras?.get("userId").toString().toInt()
        userName(userId, value)

        btnClose.setOnClickListener{
            finish()
        }

    }

    fun userName(userId : Int, value : String){
        RetrofitClient.instance.getUserName(userId)
            .enqueue(object : Callback<String> {

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    println(t.message)
                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    when {
                        response.code().toString() == "200" -> {
                            println(response.body())
                            nome.text = response.body()
                            valor.text = "R$${value}"
                        }
                        else -> {
                            println("Something are wrong!")
                            nome.text = "Error!"
                            valor.text = "R$${value}"
                        }
                    }
                }
            })
    }

}
