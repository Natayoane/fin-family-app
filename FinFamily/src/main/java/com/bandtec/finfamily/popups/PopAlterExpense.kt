package com.bandtec.finfamily.popups

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bandtec.finfamily.R
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.model.GroupTransResponse
import kotlinx.android.synthetic.main.activity_pop_alter_expense.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopAlterExpense : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_alter_expense)

        val transId = intent?.extras?.getInt("id", 0)
        val name = intent?.extras?.getString("name", "")
        val idCategory = intent?.extras?.getInt("category", 0)
        val value = intent?.extras?.getString("value", "")

        etNome.hint = name
        etValue.hint = value
        spCategory.setSelection(idCategory!! - 1)

        btnDeletePut.setOnClickListener {
            val delete = Intent(this, PopDeletePut::class.java)

            delete.putExtra("id", transId)

            startActivity(delete)
            finish()

        }

        btnClose.setOnClickListener {
            finish()
        }

        btUpdate.setOnClickListener {
            val categoryId = getExpenseId(spCategory.selectedItem.toString())
            var transaction = GroupTransResponse(
                transId,
                name!!,
                null,
                value?.toFloat(),
                null,
                null,
                null,
                null,
                null,
                categoryId,
                null,
                null,
                null,
                null,
                null
            )
            if(etNome.text!!.isNotEmpty()){
                transaction.name = etNome.text.toString()
            }
            if(etValue.text!!.isNotEmpty()){
                transaction.value = etValue.text.toString().toFloat()
            }

            updateTransaction(transId!!, transaction)
            finish()
        }
    }

    fun updateTransaction(transId: Int, transaction: GroupTransResponse) {
        RetrofitClient.instance.updateTransactions(transId, transaction)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.code().toString() == "200") {
                        Toast.makeText(
                            applicationContext,
                            "Transação alterada com sucesso!",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Houve um erro ao alterar a transação!\nTente novamente mais tarde!",
                            Toast.LENGTH_LONG
                        ).show()
                        println("Something are wrong!")
                    }


                }

            })
    }

    fun getExpenseId(category: String): Int {
        val expenseTypes = resources.getStringArray(R.array.expenses)
        var expenseId = 0
        when (category) {
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
        }
        return expenseId
    }
}
