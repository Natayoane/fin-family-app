package com.bandtec.finfamily

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.fragments.CardGoal
import com.bandtec.finfamily.fragments.GroupFinance
import com.bandtec.finfamily.model.GoalsResponse
import com.bandtec.finfamily.model.GroupTransResponse
import com.bandtec.finfamily.popups.PopNewGoal
import kotlinx.android.synthetic.main.activity_goals.*
import kotlinx.android.synthetic.main.activity_panel.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Goals : AppCompatActivity() {

    var fragSize = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goals)
        val groupId = intent.extras?.get("groupId").toString().toInt()
        println(groupId)

        getGoals(groupId)

        goalsRefresh.setOnRefreshListener {

            val frags = supportFragmentManager
            var i = 0
            while (i < fragSize) {
                val fragment = frags.findFragmentByTag("goals$i")
                frags.beginTransaction().detach(fragment!!).commit()
                i++
            }
            getGoals(groupId)
        }

        btnNewGoal.setOnClickListener {
            val newGoal = Intent(this, PopNewGoal::class.java)
            newGoal.putExtra("groupId", groupId)
            startActivity(newGoal)
        }

    }

    fun getGoals(groupId: Int) {
        goalsRefresh.isRefreshing = true
        RetrofitClient.instance.getGoals(groupId)
            .enqueue(object : Callback<List<GoalsResponse>> {
                override fun onFailure(call: Call<List<GoalsResponse>>, t: Throwable) {
                    goalsRefresh.isRefreshing = false
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<List<GoalsResponse>>,
                    response: Response<List<GoalsResponse>>
                ) {
                    goalsRefresh.isRefreshing = false
                    when {
                        response.code().toString() == "200" -> {
                            setGoals(response.body()!!)
                        }
                        response.code().toString() == "204" -> {
                            Toast.makeText(
                                applicationContext,
                                "Você ainda não possui metas! \n Que tal criar uma clicando no" +
                                        " botão '+' logo abaixo para criar sua primeira meta? ;)",
                                Toast.LENGTH_LONG
                            ).show()
                            println("No content!")
                        }
                        else -> {
                            println("Something are wrong!")
                        }
                    }
                }
            })
    }

    fun setGoals(goals: List<GoalsResponse>) {
        val goalsFrag = supportFragmentManager.beginTransaction()

        goals.forEachIndexed { i, g ->
            val parametros = Bundle()
            parametros.putInt("id", g.id!!)
            parametros.putString("name", g.name)
            parametros.putString("description", g.description)
            parametros.putFloat("value", g.value!!)
            parametros.putString("deadline", g.deadline)
            parametros.putInt("groupId", g.groupId!!)
            val goalsFragment = CardGoal()
            goalsFragment.arguments = parametros

            goalsFrag.add(R.id.goalsFrag, goalsFragment, "goals$i")

        }
        goalsFrag.commit()
        fragSize = goals.size
    }

}