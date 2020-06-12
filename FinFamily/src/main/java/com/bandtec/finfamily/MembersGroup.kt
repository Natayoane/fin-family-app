package com.bandtec.finfamily

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.fragments.Members
import com.bandtec.finfamily.fragments.MembersFamContribution
import com.bandtec.finfamily.model.UserResponse
import com.bandtec.finfamily.popups.PopAddNewMember
import kotlinx.android.synthetic.main.activity_members_group.*
import kotlinx.android.synthetic.main.activity_panel.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MembersGroup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_members_group)
        val sp: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        val extId = intent.extras?.get("extId").toString()
        val groupId = intent.extras?.get("groupId").toString().toInt()
        val groupName = intent.extras?.get("groupName").toString()
        val userId = sp.getInt("userId", 0)


        getGroupMembers(extId, groupId, groupName)

        btnGroup.setOnClickListener {

            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            sharingIntent.putExtra(
                Intent.EXTRA_TEXT, """
                Olá, estou usando o app Fin Family para marcar minhas despesas!
                Venha fazer parte do meu grupo:
                Nome do grupo: *$groupName*
                Código do grupo: *$extId*
            """.trimIndent()
            )
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Fin Family - Entre no meu grupo")
            startActivity(Intent.createChooser(sharingIntent, "Compartilhe o ID do grupo"))

        }

        btLeaveGroup.setOnClickListener {
            val login = Intent(this, Login::class.java)

            if (userId == 0) {
                Toast.makeText(
                    this,
                    """Ops, algo deu errado!
                        Por favor, tente fazer o login novamente!
                    """.trimMargin(),
                    Toast.LENGTH_LONG
                ).show()
                startActivity(login)
                finish()
            } else {
                leaveGroup(userId, groupId, groupName)
                startActivity(login)
            }

        }
    }

    private fun leaveGroup(userId: Int, groupId: Int, groupName: String) {
        RetrofitClient.instance.leaveGroup(userId, groupId)
            .enqueue(object : Callback<Any> {
                override fun onFailure(call: Call<Any>, t: Throwable) {
                    println("Passei 1")
                    println(t.message)
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<Any>,
                    response: Response<Any>
                ) {
                    when {
                        response.code().toString() == "200" -> {
                            println("Passei 2")
                            println("groupName")
                            println(response.body().toString())
                            Toast.makeText(
                                applicationContext,
                                "Você saiu do $groupName!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else -> {
                            val extract = Intent(applicationContext, Extract::class.java)
                            println("Something are wrong!")
                            startActivity(extract)
                            finish()
                        }
                    }
                }
            })
    }


    private fun getGroupMembers(extId: String, groupId: Int, groupName: String) {
        RetrofitClient.instance.getGroupMembers(extId)
            .enqueue(object : Callback<List<UserResponse>> {
                var members: List<UserResponse>? = null

                override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<List<UserResponse>>,
                    response: Response<List<UserResponse>>
                ) {
                    when {
                        response.code().toString() == "200" -> {
                            members = response.body()!!
                            val userIds = setMembers(members!!)
                            Thread.sleep(100L)
                            getEntries(groupId, groupName, userIds)
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

    fun getEntries(groupId: Int, groupName: String, userId: IntArray) {
//        extractRefreshLayout.isRefreshing = true

        userId.forEach {
            Thread.sleep(100L)
            RetrofitClient.instance.getUserEntriesTotal(groupId, it)
                .enqueue(object : Callback<Float> {
                    override fun onFailure(call: Call<Float>, t: Throwable) {
//                    extractRefreshLayout.isRefreshing = false
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<Float>,
                        response: Response<Float>
                    ) {
//                    extractRefreshLayout.isRefreshing = false
                        when {
                            response.code().toString() == "200" -> {
                                setTotal(response.body()!!, groupName)
                            }
                            response.code().toString() == "204" -> {
                                setTotal(0f, groupName)
                                println("No content!")
                            }
                            else -> {
                                println("Something are wrong!")
                            }
                        }
                    }
                })
        }

    }

    fun setMembers(members: List<UserResponse>): IntArray {
        val transaction = supportFragmentManager.beginTransaction()
        val userIds = IntArray(members.size)
        members.forEachIndexed { i, m ->
            val parametros = Bundle()
            userIds[i] = m.id!!
            parametros.putString("nickname", m.nickname)
            parametros.putString("fullname", m.fullName)
            val membersFragment = Members()
            membersFragment.arguments = parametros

            transaction.add(R.id.personFrag, membersFragment)

        }
        transaction.commit()

        return userIds
    }

    fun setTotal(total: Float, groupName: String) {
        val transaction = supportFragmentManager.beginTransaction()
        val parametros = Bundle()
        parametros.putString("groupName", groupName)
        parametros.putFloat("groupTotal", total)
        val accountItensFrag = MembersFamContribution()
        accountItensFrag.arguments = parametros
        transaction.add(R.id.totalFrag, accountItensFrag)
        transaction.commit()
    }
}
