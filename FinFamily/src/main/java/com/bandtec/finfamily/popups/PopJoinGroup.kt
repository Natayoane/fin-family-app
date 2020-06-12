package com.bandtec.finfamily.popups

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bandtec.finfamily.Group
import com.bandtec.finfamily.R
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.model.GroupsResponse
import kotlinx.android.synthetic.main.activity_pop_join_group.*
import kotlinx.android.synthetic.main.pop_activity_new_group.btnClose
import kotlinx.android.synthetic.main.pop_activity_new_group.ivFinish
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopJoinGroup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_join_group)

        val sp: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        btnClose.setOnClickListener {
            val intent = Intent(this, PopChooseGroupAction::class.java)
            //start your next activity
            startActivity(intent)
            finish()
        }

        ivFinish.setOnClickListener {
            val group = Intent(this, Group::class.java)

            val userId = sp.getInt("userId", 0)
            val externalId = etGroupCode.text.toString()

            if(externalId.isEmpty()){
                etGroupCode.error = "O campo 'ID do Grupo' deve ser preenchido!"
                etGroupCode.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.addGroupMember(userId, externalId)
                .enqueue(object : Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        println("passei aqui")
                        println(t.message)
                        println(t.stackTrace)
                        println(t.cause)


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
                                    "Sucesso!",
                                    Toast.LENGTH_LONG).show()
                                startActivity(group)
                            }
                            response.code().toString() == "409" -> {
                                Toast.makeText(
                                    applicationContext, "Você já pertence a este grupo!",
                                    Toast.LENGTH_LONG).show()
                                startActivity(group)
                            }
                            else -> {
                                Toast.makeText(applicationContext, "Verifique o código do grupo " +
                                        "e tente novamente!", Toast.LENGTH_LONG).show()
                                startActivity(group)
                            }
                        }
                    }
                })
        }
    }
}
