package com.bandtec.finfamily

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.model.GroupResponse
import kotlinx.android.synthetic.main.pop_activity_new_group.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopNewGroup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pop_activity_new_group);
        val intent = Intent(this, Group::class.java)

        val sp : SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)


        btnClose.setOnClickListener {
            startActivity(intent)
        }

        ivFinish.setOnClickListener {
            val groupName = inputNameGroup.text.toString()

            if(groupName.isEmpty()){
                inputNameGroup.error = "O grupo deve ter um nome!"
                inputNameGroup.requestFocus()
                return@setOnClickListener
            }else if(groupName.length < 4){
                inputNameGroup.error = "O grupo deve ter pelo menos 4 caracteres!"
                inputNameGroup.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.createGroup(groupName, 2, sp.getInt("id", 0))
                .enqueue(object : Callback<GroupResponse> {
                    override fun onFailure(call: Call<GroupResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<GroupResponse>,
                        response: Response<GroupResponse>
                    ) {
                        if(response.code().toString() == "201"){
                            Toast.makeText(applicationContext, response.code().toString(), Toast.LENGTH_LONG).show()
                            startActivity(intent)
                        }
                        else {
                            Toast.makeText(applicationContext, "O nome deste grupo não pode ser igual " +
                                    "a de um grupo que você possua!", Toast.LENGTH_LONG).show()
                        }


                    }

                })


        }
    }
}
