package com.bandtec.finfamily

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anychart.anychart.AnyChart
import com.anychart.anychart.DataEntry
import com.anychart.anychart.Pie
import com.anychart.anychart.ValueDataEntry
import com.bandtec.finfamily.popups.PopNewInvoice
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.model.GroupTransResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_panel.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Panel : AppCompatActivity() {

    val months = listOf("Jan", "Feb", "Mar")
    val earnings = arrayOf(500, 800, 2000)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panel)
        val sp: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)

        val groupId = intent.extras?.get("groupId").toString()
        val extId = intent.extras?.get("groupExternalId").toString()
        val groupType = intent.extras?.get("groupType").toString().toInt()
        val groupName = intent.extras?.get("groupName").toString()
        val userId = sp.getInt("userId", 0)

        getTransactions(userId)

        refreshLayout.setOnRefreshListener {
            getTransactions(userId)
            updateValues()
        }

        if(groupType == 1) btnProfile.setImageDrawable(getDrawable(R.drawable.ic_person)) else btnProfile.setImageDrawable(getDrawable(R.drawable.ic_people))

        if (groupType == 1) {
            btnProfile.setOnClickListener {
                val intent = Intent(this, Profile::class.java)
                intent.putExtra("extId", extId)
                startActivity(intent)
            }
        } else {
            btnProfile.setOnClickListener {
                val intent = Intent(this, MembersGroup::class.java)
                intent.putExtra("extId", extId)
                startActivity(intent)
            }
        }

        buttonpnextract.setOnClickListener {
            if(groupType == 1){
                val intent = Intent(this, Extract::class.java)
                intent.putExtra("groupId", groupId)
                intent.putExtra("groupType", groupType)
                intent.putExtra("groupName", groupName)
                intent.putExtra("userId", userId)
                startActivity(intent)

            } else if(groupType == 2){
                val intent = Intent(this, GroupExtract::class.java)
                intent.putExtra("groupId", groupId)
                intent.putExtra("groupType", groupType)
                intent.putExtra("groupName", groupName)
                intent.putExtra("userId", userId)
                startActivity(intent)

            }

        }


        bnthome.setOnClickListener {
            val intent = Intent(this, Group::class.java)
            // start your next activity
            startActivity(intent)
            finish()
        }

        btnadd.setOnClickListener {
            val intent = Intent(this, PopNewInvoice::class.java)
            // start your next activity
            startActivity(intent)
        }

        setupPieChart()

    }

    fun setupPieChart() {
        var pie : Pie = AnyChart.pie()

        var dataEntries : List<DataEntry> = ArrayList();

        months.forEachIndexed { i, _ ->
            dataEntries =  listOf(ValueDataEntry(months[i], earnings[i]))
            pie.setData(dataEntries)
        }


        any_chart_view.setChart(pie)


        // set chart radius
       pie.setInnerRadius("95%")

    }


    fun getTransactions(userId : Int){
        refreshLayout.isRefreshing = true
        RetrofitClient.instance.getEntries(userId)
            .enqueue(object : Callback<List<GroupTransResponse>> {
                override fun onFailure(call: Call<List<GroupTransResponse>>, t: Throwable) {
                    refreshLayout.isRefreshing = false
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<List<GroupTransResponse>>,
                    response: Response<List<GroupTransResponse>>

                ) {
                    refreshLayout.isRefreshing = false
                    when {
                        response.code().toString() == "200" -> {
                            var transactions = getSharedPreferences("transactions", Context.MODE_PRIVATE)
                            val gson = Gson()
                            val json = gson.toJson(response.body())
                            transactions.edit().putString("transactions", json).commit()
                        }
                        response.code().toString() == "204" -> {
                            Toast.makeText(
                                applicationContext,
                                "Não possui transações!",
                                Toast.LENGTH_LONG
                            ).show()
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

    fun updateValues(){

        val spTransactions = getSharedPreferences("transactions", Context.MODE_PRIVATE).all
        val gson = Gson()
        println(spTransactions.toString().removeRange(0, 14).dropLast(spTransactions.size))
        val transactions = spTransactions.toString().removeRange(0, 14).dropLast(spTransactions.size)

        val groupTransactions = gson.fromJson(transactions, Array<GroupTransResponse>::class.java).asList()

        var expense : Float = 0f
        var entry : Float = 0f

        groupTransactions.forEachIndexed() { i, _ ->
            if(groupTransactions[i].idTransactionType == 1){
                entry += groupTransactions[i].value!!
            }
            else{
                expense += groupTransactions[i].value!!
            }
        }

        //Despesas
        vlExpenses2.text = String.format("%.2f", expense)

        //Entradas/Receita
        vlEarnings2.text = String.format("%.2f", entry)
    }
}

