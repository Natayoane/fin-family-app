package com.bandtec.finfamily.Popups

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bandtec.finfamily.Group
import com.bandtec.finfamily.R
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.model.GroupParticipantsResponse
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
            val intent = Intent(this, Group::class.java)

            val userId = sp.getInt("userId", 0)
            val externalId = etGroupCode.text.toString()

            if(externalId.isEmpty()){
                etGroupCode.error = "O campo 'ID do Grupo' deve ser preenchido!"
                etGroupCode.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.addGroupMember(userId, externalId)
                .enqueue(object : Callback<GroupParticipantsResponse> {
                    override fun onFailure(call: Call<GroupParticipantsResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<GroupParticipantsResponse>,
                        response: Response<GroupParticipantsResponse>
                    ) {
                        when {
                            response.code().toString() == "200" -> {

                                Toast.makeText(
                                    applicationContext,
                                    "Sucesso!",
                                    Toast.LENGTH_LONG).show()
                                startActivity(intent)
                            }
                            response.code().toString() == "409" -> {
                                Toast.makeText(
                                    applicationContext, "Você já pertence a este grupo!",
                                    Toast.LENGTH_LONG).show()
                                startActivity(intent)
                            }
                            else -> {
                                Toast.makeText(applicationContext, "Verifique o código do grupo " +
                                        "e tente novamente!", Toast.LENGTH_LONG).show()
                                startActivity(intent)
                            }
                        }
                    }
                })
        }
    }
}
