package com.bandtec.finfamily

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.anychart.anychart.AnyChart
import com.anychart.anychart.DataEntry
import com.anychart.anychart.ValueDataEntry
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.model.GroupTransResponse
import com.bandtec.finfamily.popups.PopNewInvoice
import kotlinx.android.synthetic.main.activity_panel.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class Panel : AppCompatActivity() {

    var totalEntry = 0f
    var totalExpense = 0f
    var avaible = 0f
    var currentMonth = Calendar.getInstance().get(Calendar.MONTH);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panel)

        val sp: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)

        val groupId = intent.extras?.get("groupId").toString()
        val extId = intent.extras?.get("groupExternalId").toString()
        val groupType = intent.extras?.get("groupType").toString().toInt()
        val groupName = intent.extras?.get("groupName").toString()
        val userId = sp.getInt("userId", 0)

        mes.setSelection(currentMonth)
        var month = ""
        month = if (currentMonth + 1 < 10){
            "0${currentMonth + 1}"
        } else{
            "${currentMonth + 1}"
        }

        val meses = resources.getStringArray(R.array.meses_array)

        // access the spinner
        val spinner = findViewById<Spinner>(R.id.mes)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, meses)
            spinner.adapter = adapter
            spinner.setSelection(currentMonth)
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    var spinnerMonth = ""
                    spinnerMonth = if(position + 1< 10) {
                        "0${position + 1}"
                    } else {
                        "${position + 1}"
                    }
                    getEntries(groupId.toInt(), spinnerMonth)
                    Thread.sleep(1000L)
                    getExpenses(groupId.toInt(), spinnerMonth)
                    Toast.makeText(applicationContext, meses[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        getEntries(groupId.toInt(), month)
        Thread.sleep(1000L)
        getExpenses(groupId.toInt(), month)

        panelRefresh.setOnRefreshListener {
            getEntries(groupId.toInt(), month)
            Thread.sleep(1000L)
            getExpenses(groupId.toInt(), month)
        }

        if (groupType == 1) btnProfile.setImageDrawable(getDrawable(R.drawable.ic_baseline_person_24))
            else btnProfile.setImageDrawable(getDrawable(R.drawable.ic_people)
        )

        if (groupType == 1) {
            btnProfile.setOnClickListener {
                val profile = Intent(this, Profile::class.java)
                profile.putExtra("extId", extId)
                profile.putExtra("groupId", groupId)
                profile.putExtra("groupName", groupName)

                startActivity(profile)
            }
        } else {
            btnProfile.setOnClickListener {
                val members = Intent(this, MembersGroup::class.java)
                members.putExtra("extId", extId)
                members.putExtra("groupId", groupId)
                members.putExtra("groupName", groupName)

                startActivity(members)
            }
        }

        btGoals.setOnClickListener {
            val goals = Intent(this, Goals::class.java)
            goals.putExtra("groupId", groupId)
            goals.putExtra("userId", userId)
            // start your next activity
            startActivity(goals)
        }

        buttonpnextract.setOnClickListener {
            if (groupType == 1) {
                val intent = Intent(this, Extract::class.java)
                intent.putExtra("groupId", groupId)
                intent.putExtra("groupType", groupType)
                intent.putExtra("groupName", groupName)
                intent.putExtra("userId", userId)
                startActivity(intent)

            } else if (groupType == 2) {
                val intent = Intent(this, GroupExtract::class.java)
                intent.putExtra("groupId", groupId)
                intent.putExtra("groupType", groupType)
                intent.putExtra("groupName", groupName)
                intent.putExtra("userId", userId)
                startActivity(intent)
            }

        }



        btnadd.setOnClickListener {
            val newInvoice = Intent(this, PopNewInvoice::class.java)
            newInvoice.putExtra("groupId", groupId)
            newInvoice.putExtra("userId", userId)

            startActivity(newInvoice)
        }

    }

    fun setupPieChart() {
        val pie = AnyChart.pie()

        val data: List<DataEntry>

        if (totalEntry > 0f || totalExpense > 0f) {
            if (totalEntry > 0f && totalExpense == 0f) {
                data = listOf(
                    ValueDataEntry("Entradas", totalEntry.toDouble())
                )
                pie.setData(data)
                pie.setPalette(
                    arrayOf(
                        "#55910E"
                    )
                )
            } else if (totalExpense > 0f && totalEntry == 0f) {
                data = listOf(
                    ValueDataEntry("Saídas", totalExpense.toDouble())
                )
                pie.setData(data)
                pie.setPalette(
                    arrayOf(
                        "#CC0000"
                    )
                )
            } else if (totalEntry > 0f && totalExpense > 0f) {
                data = listOf(
                    ValueDataEntry("Entradas", totalEntry.toDouble()),
                    ValueDataEntry("Saídas", totalExpense.toDouble())
                )
                pie.setData(data)
                pie.setPalette(
                    arrayOf(
                        "#55910E",
                        "#CC0000"
                    )
                )
            }
        }
        chart.setChart(pie)
        pie.setInnerRadius("95%")
    }

    fun getEntries(groupId: Int, month : String) {
        panelRefresh.isRefreshing = true
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
                            totalEntry = setEntries(response.body()!!)
                            Thread.sleep(300L)
                        }
                        response.code().toString() == "204" -> {
                            tvTotalEntry.text = "0.00"
                            println("No content!")
                        }
                        else -> {
                            println("Something are wrong!")
                        }
                    }
                }
            })
    }

    fun getExpenses(groupId: Int, month: String) {
        RetrofitClient.instance.getExpenses(groupId, month)
            .enqueue(object : Callback<List<GroupTransResponse>> {
                override fun onFailure(call: Call<List<GroupTransResponse>>, t: Throwable) {
                    panelRefresh.isRefreshing = false
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

                override fun onResponse(
                    call: Call<List<GroupTransResponse>>,
                    response: Response<List<GroupTransResponse>>
                ) {
                    panelRefresh.isRefreshing = false
                    when {
                        response.code().toString() == "200" -> {
                            totalExpense = setExpenses(response.body()!!)
                            avaible = totalEntry - totalExpense
                            tvAvaible.text = "$avaible"
                            if(avaible > 0){
                                tvAvaible.setTextColor(Color.parseColor("#2176D3"))
                            } else {
                                tvAvaible.setTextColor(Color.parseColor("#CC0000"))
                            }
                            Thread.sleep(300L)
                            setupPieChart()
                        }
                        response.code().toString() == "204" -> {
                            tvTotalExpense.text = "0.00"
                            avaible = totalEntry - totalExpense
                            tvAvaible.text = "$avaible"
                            if(avaible > 0){
                                tvAvaible.setTextColor(Color.parseColor("#2176D3"))
                            } else {
                                tvAvaible.setTextColor(Color.parseColor("#CC0000"))
                            }
                            setupPieChart()
                            println("No content!")
                        }
                        else -> {
                            setupPieChart()
                            println("Something are wrong!")
                        }
                    }
                }
            })
    }

    fun setEntries(entries: List<GroupTransResponse>): Float {
        var total = 0f
        entries.forEach { e ->
            total += e.value!!
        }
        tvTotalEntry.text = "$total"
        return total
    }

    fun setExpenses(entries: List<GroupTransResponse>): Float {
        var total = 0f
        entries.forEach { e ->
            total += e.value!!
        }
        tvTotalExpense.text = "$total"
        return total
    }

    fun popInstruction(v: View) {
        Toast.makeText(
            this, """
            Trás seu saldo total!
            Calculo usado:
            Entradas - Saídas = Saldo
        """.trimIndent(), Toast.LENGTH_LONG
        ).show()
    }
}

