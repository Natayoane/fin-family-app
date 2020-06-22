package com.bandtec.finfamily

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.fragments.AccountItems
import com.bandtec.finfamily.model.GroupTransResponse
import com.bandtec.finfamily.popups.PopFamContribution
import kotlinx.android.synthetic.main.activity_extract.*
import kotlinx.android.synthetic.main.activity_extract.more
import kotlinx.android.synthetic.main.activity_extract.tvAvaibleAccount
import kotlinx.android.synthetic.main.activity_extract.tvGroupName
import kotlinx.android.synthetic.main.activity_extract.vlTotalFamily
import kotlinx.android.synthetic.main.activity_group_extract.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Extract : AppCompatActivity() {
    var fragSize = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extract)

        val groupId = intent.extras?.get("groupId").toString()
        val groupName = intent.extras?.get("groupName").toString()
        val month = intent.extras?.get("month").toString()
        getEntries(groupId.toInt(), groupName, month)
        Thread.sleep(200L)
        getExpenses(groupId.toInt(), month)

        extractRefresh.setOnRefreshListener {
            val frags = supportFragmentManager
            var i = 0
            while (i < fragSize) {
                val fragment = frags.findFragmentByTag("expenses$i")
                frags.beginTransaction().detach(fragment!!).commit()
                i++
            }
            getEntries(groupId.toInt(), groupName, month)
            Thread.sleep(200L)
            getExpenses(groupId.toInt(), month)
        }

        more.setOnClickListener {
            val entries = Intent(this, ModalEntry::class.java)
            entries.putExtra("groupId", groupId)
            entries.putExtra("month", month)
            startActivity(entries)
        }
//
//        btNewContribution.setOnClickListener {
//            val intent = Intent(this, PopFamContribution::class.java)
//            //start your next activity
//            startActivity(intent)
//        }

    }

    fun getExpenses(groupId: Int, month: String) {
        RetrofitClient.instance.getExpenses(groupId, month)
            .enqueue(object : Callback<List<GroupTransResponse>> {
                override fun onFailure(call: Call<List<GroupTransResponse>>, t: Throwable) {
                    extractRefresh.isRefreshing = false
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<List<GroupTransResponse>>,
                    response: Response<List<GroupTransResponse>>
                ) {
                    extractRefresh.isRefreshing = false
                    when {
                        response.code().toString() == "200" -> {
                            setExpenses(response.body()!!)
                        }
                        response.code().toString() == "204" -> {
                            tvAvaibleAccount.text = vlTotalFamily.text
                        }
                        else -> {
                            println("Something are wrong!")
                        }
                    }
                }
            })
    }

    fun getEntries(groupId: Int, groupName: String, month: String) {
//        extractRefresh.isRefreshing = true
        RetrofitClient.instance.getEntries(groupId, month)
            .enqueue(object : Callback<List<GroupTransResponse>> {
                override fun onFailure(call: Call<List<GroupTransResponse>>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<List<GroupTransResponse>>,
                    response: Response<List<GroupTransResponse>>
                ) {
                    when {
                        response.code().toString() == "200" -> {
                            setEntries(response.body()!!, groupName, (month.toInt() - 1))
                        }
                        response.code().toString() == "204" -> {
                            val meses = resources.getStringArray(R.array.meses_array)

                            tvGroupName.text = getString(
                                R.string.fam_name_and_month,
                                groupName,
                                meses[month.toInt() - 1]
                            )
                            vlTotalFamily.text = "R$0.0"
                            println("No content!")
                        }
                        else -> {
                            println("Something are wrong!")
                        }
                    }
                }
            })
    }

    fun setExpenses(expenses: List<GroupTransResponse>) {
        val transaction = supportFragmentManager.beginTransaction()
        var total = 0f
        val totalFamily = vlTotalFamily.text.toString().replace("R$", "").toFloat()

        expenses.forEachIndexed { i, e ->
            val parametros = Bundle()
            parametros.putInt("id", e.id!!)
            parametros.putString("name", e.name)
            parametros.putInt("category", e.idExpenseCategory!!)
            parametros.putFloat("value", e.value!!)
            parametros.putInt("userId", e.userId!!)
            val accountItensFrag = AccountItems()
            accountItensFrag.arguments = parametros
            transaction.add(R.id.accItensFrag, accountItensFrag, "expenses$i")
            total += e.value!!
        }
        val avaible = totalFamily - total
        tvAvaibleAccount.text = avaible.toString()
        transaction.commit()
        fragSize = expenses.size
    }

    fun setEntries(entries: List<GroupTransResponse>, groupName: String, month: Int) {
        var total = 0f
        entries.forEach { e ->
            total += e.value!!
        }
        vlTotalFamily.text = "$total"
        val meses = resources.getStringArray(R.array.meses_array)

        tvGroupName.text = getString(R.string.fam_name_and_month, groupName, meses[month])
    }
}
