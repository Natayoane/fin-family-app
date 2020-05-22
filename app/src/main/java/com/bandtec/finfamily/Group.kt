package com.bandtec.finfamily

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bandtec.finfamily.popups.PopChooseGroupAction
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.model.GroupResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_group.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Group : AppCompatActivity() {

    private var sp : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)
    
        val user = getSharedPreferences("user", Context.MODE_PRIVATE)
        val spGroups = getSharedPreferences("group", Context.MODE_PRIVATE).all
        println(user.getInt("userId", 0))
        getGroups(user.getInt("userId", 0))

        val gson = Gson()
//        val groups = spGroups.toString().removeRange(0,8).dropLast(spGroups.size)
//
//        val userGroups = gson.fromJson(groups, Array<GroupResponse>::class.java).asList()
//
//        userGroups.forEachIndexed() { i, g ->
//            println("Grupo $i: ${g.id} ")
//        }




        btnGroup.setOnClickListener {
            val intent = Intent(this, PopChooseGroupAction::class.java)
            //start your next activity
            startActivity(intent)
        }
    }

    fun Personal(v: View){
        val intent = Intent(this, Panel::class.java)
        //start your next activity
        startActivity(intent)
    }

    fun Group(v: View){
        val intent = Intent(this, Panel::class.java)

        val id:Int = 1
        intent.putExtra("id", id)

        startActivity(intent)
    }

    fun getGroups(userId : Int){
        RetrofitClient.instance.getUserGroups(userId)
            .enqueue(object : Callback<List<GroupResponse>> {
                override fun onFailure(call: Call<List<GroupResponse>>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<List<GroupResponse>>,
                    response: Response<List<GroupResponse>>
                ) {
                    when {
                        response.code().toString() == "200" -> {
                                var group = getSharedPreferences("group", Context.MODE_PRIVATE)
                                val gson = Gson()
                                val json = gson.toJson(response.body())
                                group.edit().putString("grupos", json).apply()
                        }
                        response.code().toString() == "204" -> {
                            var group = getSharedPreferences("group", Context.MODE_PRIVATE)
                            val gson = Gson()
                            val json = gson.toJson(response.body())
                            group.edit().putString("grupos", json).apply()
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
}
