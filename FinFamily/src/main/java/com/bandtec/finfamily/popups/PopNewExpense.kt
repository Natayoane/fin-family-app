package com.bandtec.finfamily.popups

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bandtec.finfamily.Group
import com.bandtec.finfamily.R
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.model.GroupTransResponse
import com.bandtec.finfamily.utils.MaskEditUtil
import kotlinx.android.synthetic.main.activity_pop_new_entry.*
import kotlinx.android.synthetic.main.activity_pop_new_expense.*
import kotlinx.android.synthetic.main.activity_pop_new_expense.btSaveEntry
import kotlinx.android.synthetic.main.activity_pop_new_expense.cbRecurrent
import kotlinx.android.synthetic.main.activity_pop_new_expense.etDate
import kotlinx.android.synthetic.main.activity_pop_new_expense.etName
import kotlinx.android.synthetic.main.activity_pop_new_expense.etValue
import kotlinx.android.synthetic.main.activity_pop_new_expense.spType
import kotlinx.android.synthetic.main.activity_pop_new_goal.*
import kotlinx.android.synthetic.main.activity_pop_new_invoice.btnClose
import kotlinx.android.synthetic.main.fragment_account_items.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class PopNewExpense : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_new_expense)
        etDate.addTextChangedListener(MaskEditUtil.mask(etDate, MaskEditUtil.FORMAT_DATE))

        val groups = Intent(applicationContext, Group::class.java)


        btnClose.setOnClickListener {
            finish()
        }
        btSaveEntry.setOnClickListener {
            val userId = intent.extras?.get("userId").toString().toInt()
            val groupId = intent.extras?.get("groupId").toString().toInt()
            val entryName = etName.text.toString()
            val entryValue = etValue.text.toString()
            val expenseType = spType.selectedItem.toString()
            val isRecurrent = cbRecurrent.isChecked
            val recurrenteTypeId: Int
            val expenseTypeId = getExpenseId(expenseType)
            val payDate = etDate.text.toString()


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


            if(payDate.isEmpty()){
                etDate.error = "Data de pagamento é um campo obrigatório!"
                etDate.requestFocus()
                return@setOnClickListener
            }


            recurrenteTypeId = if (isRecurrent) {
                5
            } else {
                1
            }

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
                expenseTypeId,
                null,
                2,
                null,
                null,
                null
            )
            createTransaction(transaction)
            startActivity(groups)
            finish()
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

    fun getExpenseId(entryType: String): Int {
        val expenseTypes = resources.getStringArray(R.array.expenses)
        var expenseId = 0
        when (entryType) {
            expenseTypes[0] -> expenseId = 1
            expenseTypes[1] -> expenseId = 2
            expenseTypes[2] -> expenseId = 3
            expenseTypes[3] -> expenseId = 4
            expenseTypes[4] -> expenseId = 5
            expenseTypes[5] -> expenseId = 6
            expenseTypes[6] -> expenseId = 7
            expenseTypes[7] -> expenseId = 8
            expenseTypes[8] -> expenseId = 9
            expenseTypes[9] -> expenseId = 10
            expenseTypes[10] -> expenseId = 11
            expenseTypes[11] -> expenseId = 12
            expenseTypes[12] -> expenseId = 13
            expenseTypes[13] -> expenseId = 14
            expenseTypes[14] -> expenseId = 15
            expenseTypes[15] -> expenseId = 16
            expenseTypes[16] -> expenseId = 17
            expenseTypes[17] -> expenseId = 18
            expenseTypes[18] -> expenseId = 19
            expenseTypes[19] -> expenseId = 21
            expenseTypes[20] -> expenseId = 21
            expenseTypes[21] -> expenseId = 22
            expenseTypes[22] -> expenseId = 23
            expenseTypes[23] -> expenseId = 24
            expenseTypes[24] -> expenseId = 25
            expenseTypes[25] -> expenseId = 26
            expenseTypes[26] -> expenseId = 27
            expenseTypes[27] -> expenseId = 28
            expenseTypes[28] -> expenseId = 29
            expenseTypes[29] -> expenseId = 30
            expenseTypes[30] -> expenseId = 31
            expenseTypes[31] -> expenseId = 32
            expenseTypes[32] -> expenseId = 33
        }
        return expenseId
    }
}