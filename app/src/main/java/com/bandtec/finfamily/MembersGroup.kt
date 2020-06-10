package com.bandtec.finfamily

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.fragments.Members
import com.bandtec.finfamily.model.UserResponse
import com.bandtec.finfamily.popups.PopAddNewMember
import kotlinx.android.synthetic.main.activity_members_group.*
import kotlinx.android.synthetic.main.fragment_members.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MembersGroup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_members_group)
        val sp: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        val extId = intent.extras?.get("extId").toString()

        getGroupMembers(extId)

        btnGroup.setOnClickListener {
            val intent = Intent(this, PopAddNewMember::class.java)
            //start your next activity
            startActivity(intent)
        }
    }

    fun getGroupMembers(extId: String){
        RetrofitClient.instance.getGroupMembers(extId)
            .enqueue(object : Callback<List<UserResponse>> {
                var members : List<UserResponse>? = null

                override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<List<UserResponse>>,
                    response: Response<List<UserResponse>>
                ) {
                    when {
                        response.code().toString() == "200" -> {
                            println(response.body())
                            members = response.body()!!
                            setMembers(members!!)
                        }
                        response.code().toString() == "204" -> {
                            println("Something are wrong!!")
                        }
                        else -> {
                            Toast.makeText(
                                applicationContext,
                                "Erro interno no servidor!",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            })
    }

    fun setMembers(members : List<UserResponse>){
        val transaction = supportFragmentManager.beginTransaction()

        members.forEachIndexed { _, m ->
            val parametros = Bundle()
            parametros.putString("nickname", m.nickname)
            parametros.putString("fullname", m.fullName)
            val membersFragment = Members()
            membersFragment.arguments = parametros

            transaction.add(R.id.groupMembers, membersFragment, "group1")

        }
        transaction.commit()
    }
}
