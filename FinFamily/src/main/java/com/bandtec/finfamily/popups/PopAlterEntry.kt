package com.bandtec.finfamily.popups

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bandtec.finfamily.R
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.model.GroupTransResponse
import kotlinx.android.synthetic.main.activity_pop_alter_entry.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopAlterEntry : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_alter_entry)
        val transId = intent?.extras?.getInt("id", 0)
        val name = intent?.extras?.getString("name", "")
        val idCategory = intent?.extras?.getInt("category", 0)
        val value = intent?.extras?.getString("value", "")

        etNome.hint = name
        etValue.hint = value
        spCategory.setSelection(idCategory!! - 1)

        btnDeletePut.setOnClickListener {
            val delete = Intent(this, PopDeletePut::class.java)

            delete.putExtra("entryId", transId)

            startActivity(delete)

        }

        btnClose.setOnClickListener {
            finish()
        }

        btUpdate.setOnClickListener {
            val categoryId = getentryId(spCategory.selectedItem.toString())
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
                null,
                categoryId,
                null,
                null,
                null
            )
            if(etNome.text.isNotEmpty()){
                transaction.name = etNome.text.toString()
            }
            if(etValue.text.isNotEmpty()){
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

    fun getentryId(category: String): Int {
        val entryTypes = resources.getStringArray(R.array.entry)
        var entryId = 0
        when (category) {
            entryTypes[0] -> entryId = 1
            entryTypes[1] -> entryId = 2
            entryTypes[2] -> entryId = 3
            entryTypes[3] -> entryId = 4
            entryTypes[4] -> entryId = 5
        }
        return entryId
    }
}