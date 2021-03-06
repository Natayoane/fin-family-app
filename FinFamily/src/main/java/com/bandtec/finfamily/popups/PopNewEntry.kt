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
import kotlinx.android.synthetic.main.activity_pop_new_entry.etDate
import kotlinx.android.synthetic.main.activity_pop_new_entry.etName
import kotlinx.android.synthetic.main.activity_pop_new_entry.etValue
import kotlinx.android.synthetic.main.activity_pop_new_invoice.btnClose
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopNewEntry : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_new_entry)
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
            val entryType = spType.selectedItem.toString()
            val isRecurrent = cbRecurrent.isChecked
            val recurrenteTypeId: Int
            val expenseId = getEntryId(entryType)
            val payDate = etDate.text.toString()


            if (entryName.isEmpty()) {
                etName.error = getString(R.string.transaction_name)
                etName.requestFocus()
                return@setOnClickListener
            }
            if (entryValue.isEmpty()) {
                etValue.error = getString(R.string.transaction_value)
                etValue.requestFocus()
                return@setOnClickListener
            }

            if (payDate.isEmpty()) {
                etDate.error = getString(R.string.transaction_pay_date)
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
                null,
                expenseId,
                1,
                null,
                null,
                null
            )
            createTransaction(transaction)
            groups.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(groups)
        }
    }

    fun createTransaction(transaction: GroupTransResponse) {

        RetrofitClient.instance.createTransaction(transaction)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(applicationContext, getString(R.string.default_error), Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    when {
                        response.code().toString() == "201" -> {
                            Toast.makeText(
                                applicationContext,
                                getString(R.string.transaction_added),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        else -> {
                            Toast.makeText(applicationContext, getString(R.string.default_error), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            })
    }

    fun getEntryId(entryType: String): Int {
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
