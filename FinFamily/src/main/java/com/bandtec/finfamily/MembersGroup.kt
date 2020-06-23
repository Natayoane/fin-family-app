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
import kotlinx.android.synthetic.main.activity_members_group.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MembersGroup : AppCompatActivity() {

    var fragSize = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_members_group)
        val sp: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        val extId = intent.extras?.get("extId").toString()
        val groupId = intent.extras?.get("groupId").toString().toInt()
        val groupName = intent.extras?.get("groupName").toString()
        val userId = sp.getInt("userId", 0)
        val month = intent.extras?.get("month").toString()

        getGroupMembers(extId, groupId, groupName, month)

        groupMembersRefresh.setOnRefreshListener {
            val frags = supportFragmentManager
            var i = 0
            while (i < fragSize) {
                val groupMember = frags.findFragmentByTag("groupMember$i")
                frags.beginTransaction().detach(groupMember!!).commit()
                val groupContrib = frags.findFragmentByTag("memberContrib$i")
                frags.beginTransaction().detach(groupContrib!!).commit()
                i++
            }
            getGroupMembers(extId, groupId, groupName, month)
        }

        btnGroup.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            sharingIntent.putExtra(
                Intent.EXTRA_TEXT, getString(R.string.invite_new_member_text, groupName, extId)
            )
            sharingIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                getString(R.string.invite_new_member_title)
            )
            startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_group_id)))
        }

        btLeaveGroup.setOnClickListener {
            val login = Intent(this, Login::class.java)

            if (userId == 0) {
                Toast.makeText(
                    this,
                    getString(R.string.default_error),
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
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.default_error),
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onResponse(
                    call: Call<Any>,
                    response: Response<Any>
                ) {
                    when {
                        response.code().toString() == "200" -> {
                            Toast.makeText(
                                applicationContext,
                                getString(R.string.group_get_out, groupName),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else -> {
                            val extract = Intent(applicationContext, Extract::class.java)
                            getString(R.string.default_error)
                            startActivity(extract)
                            finish()
                        }
                    }
                }
            })
    }


    private fun getGroupMembers(extId: String, groupId: Int, groupName: String, month: String) {
        groupMembersRefresh.isRefreshing = true

        RetrofitClient.instance.getGroupMembers(extId)
            .enqueue(object : Callback<List<UserResponse>> {
                var members: List<UserResponse>? = null

                override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.default_error),
                        Toast.LENGTH_LONG
                    ).show()
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
                            getEntries(groupId, groupName, userIds, month)
                        }
                        response.code().toString() == "204" -> {
                            Toast.makeText(
                                applicationContext,
                                getString(R.string.default_error),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        else -> {
                            Toast.makeText(
                                applicationContext,
                                getString(R.string.default_error),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            })
    }

    fun getEntries(groupId: Int, groupName: String, userId: IntArray, month: String) {

        userId.forEachIndexed { i, it ->
            Thread.sleep(100L)
            RetrofitClient.instance.getUserEntriesTotal(groupId, it, month)
                .enqueue(object : Callback<Float> {
                    override fun onFailure(call: Call<Float>, t: Throwable) {
                        groupMembersRefresh.isRefreshing = false
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.default_error),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onResponse(
                        call: Call<Float>,
                        response: Response<Float>
                    ) {
                        groupMembersRefresh.isRefreshing = false
                        when {
                            response.code().toString() == "200" -> {
                                setTotal(response.body()!!, groupName, i)
                            }
                            response.code().toString() == "204" -> {
                                setTotal(0f, groupName, i)
                            }
                            else -> {
                                Toast.makeText(
                                    applicationContext,
                                    getString(R.string.default_error),
                                    Toast.LENGTH_LONG
                                ).show()
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

            transaction.add(R.id.personFrag, membersFragment, "groupMember$i")
        }
        transaction.commit()
        fragSize = members.size
        return userIds
    }

    fun setTotal(total: Float, groupName: String, fragItem: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        val parametros = Bundle()
        parametros.putString("groupName", groupName)
        parametros.putFloat("groupTotal", total)
        val accountItensFrag = MembersFamContribution()
        accountItensFrag.arguments = parametros
        transaction.add(R.id.totalFrag, accountItensFrag, "memberContrib$fragItem")
        transaction.commit()
    }
}
