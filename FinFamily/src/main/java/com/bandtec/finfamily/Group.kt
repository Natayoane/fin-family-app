package com.bandtec.finfamily

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.fragments.GroupFinance
import com.bandtec.finfamily.model.GroupResponse
import com.bandtec.finfamily.popups.PopChooseGroupAction
import kotlinx.android.synthetic.main.activity_group.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference


class Group : AppCompatActivity() {

    private var sp: SharedPreferences? = null
    var fragSize = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)


        val user = getSharedPreferences("user", Context.MODE_PRIVATE)
        val userId = user.getInt("userId", 0)

        getUserGroups(userId)

        groupRefresh.setOnRefreshListener {

            val frags = supportFragmentManager
            var i = 0
            while( i < fragSize){
                val fragment = frags.findFragmentByTag("group$i")
                frags.beginTransaction().detach(fragment!!).commit()
                i++
            }
            getUserGroups(userId)
        }

        btnGroup.setOnClickListener {
            val intent = Intent(this, PopChooseGroupAction::class.java)
            //start your next activity
            startActivity(intent)
        }
    }

    fun Group(v: View) {
        val intent = Intent(this, Panel::class.java)

        val id: Int = 1
        intent.putExtra("id", id)

        startActivity(intent)
    }

    fun getUserGroups(userId: Int) {
        groupRefresh.isRefreshing = true

        RetrofitClient.instance.getUserGroups(userId)
            .enqueue(object : Callback<List<GroupResponse>> {
                var groups: List<GroupResponse>? = null

                override fun onFailure(call: Call<List<GroupResponse>>, t: Throwable) {
                    groupRefresh.isRefreshing = false

                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<List<GroupResponse>>,
                    response: Response<List<GroupResponse>>
                ) {
                    groupRefresh.isRefreshing = false

                    when {
                        response.code().toString() == "200" -> {
                            groups = response.body()!!
                            println(groups?.size)
                            setGroups(groups!!)
                        }
                        response.code().toString() == "204" -> {
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

    fun setGroups(userGroups: List<GroupResponse>) {
        val transaction = supportFragmentManager.beginTransaction()

        userGroups.forEachIndexed { i, g ->
            val parametros = Bundle()
            parametros.putInt("groupId", g.id!!)
            parametros.putString("groupName", g.groupName)
            parametros.putInt("groupType", g.groupType!!)
            parametros.putInt("groupOwner", g.groupOwner!!)
            parametros.putString("groupExternalId", g.externalGroupId)
            val groupsFragments = GroupFinance()
            groupsFragments.arguments = parametros

            transaction.add(R.id.groupFinanceFrag, groupsFragments, "group$i")

        }
        transaction.commit()
        fragSize = userGroups.size
    }


}

