package com.bandtec.finfamily

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.fragments.AccountItems
import com.bandtec.finfamily.model.GroupTransResponse
import com.bandtec.finfamily.popups.PopFamContribution
import kotlinx.android.synthetic.main.activity_extract.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Extract : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extract)

        getTransactions(1)
        val total = intent.extras?.getFloat("totalFamily")

        btNewContribution.setOnClickListener {
            val intent = Intent(this, PopFamContribution::class.java)
            //start your next activity
            startActivity(intent)
            finish()
        }

    }

    fun getTransactions(groupId : Int){
        RetrofitClient.instance.getExpenses(groupId)
            .enqueue(object : Callback<List<GroupTransResponse>> {
                override fun onFailure(call: Call<List<GroupTransResponse>>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<List<GroupTransResponse>>,
                    response: Response<List<GroupTransResponse>>
                ) {
                    if (response.code().toString() == "200") {
                        setExpenses(response.body()!!)
                    } else {
                        println("Something are wrong!")
                    }
                }
            })
    }


    fun setExpenses(expenses : List<GroupTransResponse>){
        val transaction = supportFragmentManager.beginTransaction()
        expenses.forEach {e ->
            val parametros = Bundle()
            parametros.putInt("id", e.id!!)
            parametros.putString("name", e.name)
            parametros.putInt("category", e.idExpenseCategory!!)
            parametros.putFloat("value", e.value!!)
            val accountItensFrag = AccountItems()
            accountItensFrag.arguments = parametros
            transaction.add(R.id.accItensFrag, accountItensFrag)
        }
        transaction.commit()
    }
}
