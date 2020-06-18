package com.bandtec.finfamily.popups

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bandtec.finfamily.R
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.model.GoalsTransResponse
import kotlinx.android.synthetic.main.activity_pop_entry_output_goal.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopEntryOutputGoal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_entry_output_goal)

        val userId = intent.extras?.getInt("userId", 0)
        val groupId = intent.extras?.getInt("groupId", 0)
        val goalId = intent.extras?.getInt("goalId", 0)
        var invoiceType = 0

        println("$userId, $groupId, $goalId")

        btnClose.setOnClickListener {
            finish()
        }

        etTypeEntry.setOnClickListener {
            if (etTypeOutPut.isChecked) {
                etTypeOutPut.isChecked = false
            }
            invoiceType = 1
            println("Invoice Type: $invoiceType")
        }

        etTypeOutPut.setOnClickListener {
            if (etTypeEntry.isChecked) {
                etTypeEntry.isChecked = false
            }
            invoiceType = 2
            println("Invoice Type: $invoiceType")
        }

        btSaveGoal.setOnClickListener {
            val reason = etDescription.text.toString()
            val value = etValue.text.toString().toFloat()
            val transaction =
                GoalsTransResponse(null, reason, value, invoiceType, goalId, groupId, userId)

            createTransaction(transaction)
            finish()
        }
    }

    fun createTransaction(transaction: GoalsTransResponse) {
        RetrofitClient.instance.createGoalTrans(transaction)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    when {
                        response.code().toString() == "201" -> {
                            Toast.makeText(
                                applicationContext,
                                "Transação adicionada com sucesso!",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        else -> {
                            Toast.makeText(
                                applicationContext,
                                "Houve um erro ao adicionar a transação!\n Por favor, tente novamente mais tarde!",
                                Toast.LENGTH_LONG
                            ).show()
                            println("Something are wrong!")
                        }
                    }
                }
            })
    }

}