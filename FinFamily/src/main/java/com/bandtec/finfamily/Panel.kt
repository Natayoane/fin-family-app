package com.bandtec.finfamily

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.fragments.PieChart
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
    var currentMonth = Calendar.getInstance().get(Calendar.MONTH);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panel)
        mes.setSelection(currentMonth)

        val sp: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)

        val groupId = intent.extras?.get("groupId").toString()
        val extId = intent.extras?.get("groupExternalId").toString()
        val groupType = intent.extras?.get("groupType").toString().toInt()
        val groupName = intent.extras?.get("groupName").toString()
        val userId = sp.getInt("userId", 0)
        val meses = resources.getStringArray(R.array.meses_array)
        val spinner = findViewById<Spinner>(R.id.mes)

        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, meses
            )
            spinner.adapter = adapter
            spinner.setSelection(currentMonth)
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    val spinnerMonth = if (position + 1 < 10) {
                        "0${position + 1}"
                    } else {
                        "${position + 1}"
                    }
                    getEntries(groupId.toInt(), spinnerMonth)
                    Thread.sleep(500L)
                    getExpenses(groupId.toInt(), spinnerMonth)

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    println("Something are wrong!")
                }
            }
        }


        panelRefresh.setOnRefreshListener {
            mes.setSelection(currentMonth)
            val month = if (currentMonth + 1 < 10) {
                "0${currentMonth + 1}"
            } else {
                "${currentMonth + 1}"
            }
            getEntries(groupId.toInt(), month)
            Thread.sleep(500L)
            getExpenses(groupId.toInt(), month)
        }

        if (groupType == 1) btnProfile.setImageDrawable(getDrawable(R.drawable.ic_baseline_person_24))
        else btnProfile.setImageDrawable(
            getDrawable(R.drawable.ic_people)
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
                val month = if (mes.selectedItemId + 1 < 10) {
                    "0${mes.selectedItemId + 1}"
                } else {
                    "${mes.selectedItemId + 1}"
                }
                val members = Intent(this, MembersGroup::class.java)
                members.putExtra("extId", extId)
                members.putExtra("groupId", groupId)
                members.putExtra("groupName", groupName)
                members.putExtra("month", month)

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
                if(mes.selectedItemId + 1 < 9){
                    intent.putExtra("month", "0${mes.selectedItemId + 1}")
                } else {
                    intent.putExtra("month", "${mes.selectedItemId + 1}")
                }
                startActivity(intent)

            } else if (groupType == 2) {
                val intent = Intent(this, GroupExtract::class.java)
                intent.putExtra("groupId", groupId)
                intent.putExtra("groupType", groupType)
                intent.putExtra("groupName", groupName)
                intent.putExtra("userId", userId)
                if(mes.selectedItemId + 1 < 9){
                    intent.putExtra("month", "0${mes.selectedItemId + 1}")
                } else {
                    intent.putExtra("month", "${mes.selectedItemId + 1}")
                }
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

    fun getEntries(groupId: Int, month: String) {
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
                            Thread.sleep(1000L)
                        }
                        response.code().toString() == "204" -> {
                            totalEntry = 0f
                            Thread.sleep(1000L)
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
                            Thread.sleep(1000L)
                            createChart(totalEntry.toDouble(), totalExpense.toDouble())
                        }
                        response.code().toString() == "204" -> {
                            tvTotalExpense.text = "0.00"
                            totalExpense = 0f
                            Thread.sleep(1000L)
                            createChart(totalEntry.toDouble(), totalExpense.toDouble())
                            println("No content!")
                        }
                        else -> {
//                            setupPieChart()
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
        tvTotalEntry.text = String.format("%.2f", total)
        return if (total > 0) {
            total
        } else {
            0f
        }
    }

    fun setExpenses(entries: List<GroupTransResponse>): Float {
        var total = 0f
        entries.forEach { e ->
            total += e.value!!
        }
        tvTotalExpense.text = String.format("%.2f", total)
        return if (total > 0) {
            total
        } else {
            0f
        }
    }

    fun createChart(entry: Double, expense: Double) {
        val transaction = supportFragmentManager.beginTransaction()
        val parametros = Bundle()
        parametros.putDouble("entry", entry)
        parametros.putDouble("expense", expense)
        val chart = PieChart()
        chart.arguments = parametros

        transaction.replace(R.id.chart, chart, "chart")

        transaction.commit()
    }
}

