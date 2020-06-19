package com.bandtec.finfamily

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.fragments.AccountItems
import com.bandtec.finfamily.model.GroupTransResponse
import com.bandtec.finfamily.popups.PopFamContribution
import kotlinx.android.synthetic.main.activity_group_extract.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GroupExtract : AppCompatActivity() {
    var fragSize = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_extract)

        val groupId = intent.extras?.get("groupId").toString().toInt()
//        val groupType = intent.extras?.get("groupType").toString().toInt()
        val groupName = intent.extras?.get("groupName").toString()
        val userId = intent.extras?.get("userId").toString().toInt()
        val month = intent.extras?.get("month").toString()
        getEntries(groupId, groupName, month)
        getUserEntries(groupId, userId)
        Thread.sleep(200L)
        getExpenses(groupId, month)


        groupExtractRefresh.setOnRefreshListener {
            val frags = supportFragmentManager
            var i = 0
            while( i < fragSize){
                val fragment = frags.findFragmentByTag("expenses$i")
                frags.beginTransaction().detach(fragment!!).commit()
                i++
            }
            getEntries(groupId, groupName, month)
            getUserEntries(groupId, userId)
            Thread.sleep(200L)
            getExpenses(groupId, month)
        }
        more.setOnClickListener {
            val entries = Intent(this, ModalEntry::class.java)
            entries.putExtra("groupId", groupId)
            entries.putExtra("month", month)
            startActivity(entries)
        }

//        btNewContribution.setOnClickListener {
//            val intent = Intent(this, PopFamContribution::class.java)
//            startActivity(intent)
//            finish()
//        }

    }

    fun getExpenses(groupId : Int, month : String){
        RetrofitClient.instance.getExpenses(groupId, month)
            .enqueue(object : Callback<List<GroupTransResponse>> {
                override fun onFailure(call: Call<List<GroupTransResponse>>, t: Throwable) {
                    groupExtractRefresh.isRefreshing = false
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<List<GroupTransResponse>>,
                    response: Response<List<GroupTransResponse>>
                ) {
                    groupExtractRefresh.isRefreshing = false
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

    fun getEntries(groupId : Int, groupName: String, month : String){
        groupExtractRefresh.isRefreshing = true
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
                            setGroupEntries(response.body()!!, groupName)
                        }
                        response.code().toString() == "204" -> {
                            tvGroupName.text = groupName
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

    fun getUserEntries(groupId : Int, userId : Int){
        RetrofitClient.instance.getUserEntries(groupId, userId)
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
                            setUserEntries(response.body()!!)
                        }
                        response.code().toString() == "204" -> {
                            tvTotalUser.text = "R$0.0"
                            println("No content!")
                        }
                        else -> {
                            println("Something are wrong!")
                        }
                    }
                }
            })
    }



    fun setExpenses(expenses : List<GroupTransResponse>){
        val transaction = supportFragmentManager.beginTransaction()
        var total = 0f
        val totalFamily = vlTotalFamily?.text.toString().replace("R$", "").toFloat()
        expenses.forEachIndexed {i, e ->
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

    fun setGroupEntries(entries : List<GroupTransResponse>, groupName : String){
        var total = 0f
        entries.forEach { e ->
            total += e.value!!
        }
        vlTotalFamily.text = total.toString()
        tvGroupName.text = groupName
    }

    fun setUserEntries(entries : List<GroupTransResponse>){
        var total = 0f
        entries.forEach { e ->
            total += e.value!!
        }
        tvTotalUser.text = "$total"
    }
}
