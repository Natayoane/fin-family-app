package com.bandtec.finfamily

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.anychart.anychart.AnyChart
import com.anychart.anychart.AnyChartView
import com.anychart.anychart.DataEntry
import com.anychart.anychart.ValueDataEntry
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.model.GroupResponse
import com.bandtec.finfamily.model.GroupTransactionsResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_panel.*
import kotlinx.android.synthetic.main.activity_pop_new_invoice.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Panel : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panel)

        refreshLayout.setOnRefreshListener {
            getTransactions(1)
        }

        getTransactions(1)

        val sp: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)

        val id = 0
        if(id!! == 0 ) btnProfile.setImageDrawable(getDrawable(R.drawable.ic_person)) else btnProfile.setImageDrawable(getDrawable(R.drawable.ic_people))

        if (id!! == 0) {
            btnProfile.setOnClickListener {
                val intent = Intent(this, Profile::class.java)
                startActivity(intent)
            }
        } else {
            btnProfile.setOnClickListener {
                val intent = Intent(this, MembersGroup::class.java)
                startActivity(intent)
            }
        }

        buttonpnextract.setOnClickListener {
            val intent = Intent(this, Extract::class.java)
            // start your next activity
            startActivity(intent)
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

        val pie = AnyChart.pie()

        val data: MutableList<DataEntry> = ArrayList()
        data.add(ValueDataEntry("John", 10000))
        data.add(ValueDataEntry("Jake", 12000))
        data.add(ValueDataEntry("Peter", 18000))

        val anyChartView = any_chart_view as AnyChartView
        anyChartView.setChart(pie)
    }


    fun getTransactions(userId : Int){
        refreshLayout.isRefreshing = true
        RetrofitClient.instance.getTransactions(userId)
            .enqueue(object : Callback<List<GroupTransactionsResponse>> {
                override fun onFailure(call: Call<List<GroupTransactionsResponse>>, t: Throwable) {
                    refreshLayout.isRefreshing = false
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<List<GroupTransactionsResponse>>,
                    response: Response<List<GroupTransactionsResponse>>

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

        val spTransactions = getSharedPreferences("transactions", Context.MODE_PRIVATE).all
        val gson = Gson()
        println(spTransactions.toString().removeRange(0, 14).dropLast(spTransactions.size))
        val transactions = spTransactions.toString().removeRange(0, 14).dropLast(spTransactions.size)

        val groupTransactions = gson.fromJson(transactions, Array<GroupTransactionsResponse>::class.java).asList()

//        groupTransactions.forEachIndexed() { i, g ->
//            println("Grupo $i: ${g.value} ")
//        }

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

