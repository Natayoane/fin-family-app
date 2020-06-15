package com.bandtec.finfamily.popups

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bandtec.finfamily.Group
import com.bandtec.finfamily.Panel
import com.bandtec.finfamily.R
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.model.GroupTransResponse
import kotlinx.android.synthetic.main.activity_pop_new_entry.*
import kotlinx.android.synthetic.main.activity_pop_new_invoice.btnClose
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class PopNewEntry : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_new_entry)
        val groups = Intent(applicationContext, Group::class.java)

        btnClose.setOnClickListener {
            finish()
        }

        btSaveEntry.setOnClickListener {
            val userId = intent.extras?.get("userId").toString().toInt()
            val groupId = intent.extras?.get("groupId").toString().toInt()
            val entryName = etName.text.toString()
            val entryValue = etValue.text.toString()
            val entryType = spType.selectedItem.toString()
            val isRecurrent = cbRecurrent.isChecked
            var recurrenteTypeId: Int
            var expenseId = getEntryId(entryType)

            if (entryName.isEmpty()) {
                etName.error = "Dê um nome para essa entrada!"
                etName.requestFocus()
                return@setOnClickListener
            }
            if (entryValue.isEmpty()) {
                etValue.error = "Coloque o valor para essa entrada!"
                etValue.requestFocus()
                return@setOnClickListener
            }

            recurrenteTypeId = if (isRecurrent) {
                5
            } else {
                1
            }

            val payDate: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
            val transaction = GroupTransResponse(
                0,
                entryName,
                "",
                entryValue.toFloat(),
                payDate,
                isRecurrent,
                groupId,
                userId,
                recurrenteTypeId,
                null,
                expenseId,
                1,
                null,
                null
            )
            createTransaction(transaction)
            startActivity(groups)
        }
    }

    fun createTransaction(transaction: GroupTransResponse) {

        RetrofitClient.instance.createTransaction(transaction)
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
                            println("Something are wrong!")
                        }
                    }
                }
            })
    }

    fun getEntryId(entryType : String) : Int {
        val entriesTypes = resources.getStringArray(R.array.entry)
        var expenseId = 0
        when (entryType) {
            entriesTypes[0] -> expenseId = 1
            entriesTypes[1] -> expenseId = 2
            entriesTypes[2] -> expenseId = 3
            entriesTypes[3] -> expenseId = 4
            entriesTypes[4] -> expenseId = 5
        }
        return expenseId
    }
}
