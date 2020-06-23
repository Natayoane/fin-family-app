package com.bandtec.finfamily

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.fragments.CardGoal
import com.bandtec.finfamily.model.GoalsResponse
import com.bandtec.finfamily.popups.PopNewGoal
import kotlinx.android.synthetic.main.activity_goals.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Goals : AppCompatActivity() {

    var fragSize = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goals)
        val groupId = intent.extras?.get("groupId").toString().toInt()
        val userId = intent.extras?.get("userId").toString().toInt()

        getGoals(groupId, userId)

        goalsRefresh.setOnRefreshListener {

            val frags = supportFragmentManager
            var i = 0
            while (i < fragSize) {
                val fragment = frags.findFragmentByTag("goals$i")
                frags.beginTransaction().detach(fragment!!).commit()
                i++
            }
            getGoals(groupId, userId)
        }

        btnNewGoal.setOnClickListener {
            val newGoal = Intent(this, PopNewGoal::class.java)
            newGoal.putExtra("groupId", groupId)
            startActivity(newGoal)
        }
    }

    fun getGoals(groupId: Int, userId: Int) {
        goalsRefresh.isRefreshing = true
        RetrofitClient.instance.getGoals(groupId)
            .enqueue(object : Callback<List<GoalsResponse>> {
                override fun onFailure(call: Call<List<GoalsResponse>>, t: Throwable) {
                    goalsRefresh.isRefreshing = false
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.default_error),
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onResponse(
                    call: Call<List<GoalsResponse>>,
                    response: Response<List<GoalsResponse>>
                ) {
                    goalsRefresh.isRefreshing = false
                    when {
                        response.code().toString() == "200" -> {
                            setGoals(response.body()!!, userId)
                        }
                        response.code().toString() == "204" -> {
                            Toast.makeText(
                                applicationContext,
                                getString(R.string.goals_no_content),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        else -> {
                            getString(R.string.default_error)
                        }
                    }
                }
            })
    }

    fun setGoals(goals: List<GoalsResponse>, userId: Int) {
        val goalsFrag = supportFragmentManager.beginTransaction()

        goals.forEachIndexed { i, g ->
            val parametros = Bundle()
            parametros.putInt("id", g.id!!)
            parametros.putString("name", g.name)
            parametros.putString("description", g.description)
            parametros.putFloat("value", g.value!!)
            parametros.putString("deadline", g.deadline)
            parametros.putInt("groupId", g.groupId!!)
            parametros.putInt("userId", userId)
            val goalsFragment = CardGoal()
            goalsFragment.arguments = parametros

            goalsFrag.add(R.id.goalsFrag, goalsFragment, "goals$i")

        }
        goalsFrag.commit()
        fragSize = goals.size
    }

}