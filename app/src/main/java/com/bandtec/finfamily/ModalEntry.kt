package com.bandtec.finfamily

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.fragments.ListEntry
import com.bandtec.finfamily.model.GroupTransResponse
import com.bandtec.finfamily.popups.PopAlterPut
import kotlinx.android.synthetic.main.activity_pop_new_invoice.btnClose
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModalEntry : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modal_entry)

        getEntries(1)

        btnClose.setOnClickListener {
            val intent = Intent(this, Extract::class.java)
            //start your next activity
            startActivity(intent)
            finish()
        }
    }

    fun alterPut(v:View){
        val intent = Intent(this, PopAlterPut::class.java)
        //start your next activity
        startActivity(intent)
        finish()
    }

    fun getEntries(groupId : Int){
        RetrofitClient.instance.getEntries(groupId)
            .enqueue(object : Callback<List<GroupTransResponse>> {
                override fun onFailure(call: Call<List<GroupTransResponse>>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<List<GroupTransResponse>>,
                    response: Response<List<GroupTransResponse>>
                ) {
                    if (response.code().toString() == "200") {
                        setEntries(response.body()!!)
                    } else {
                        println("Something are wrong!")
                    }
                }
            })
    }

    fun setEntries(entries : List<GroupTransResponse>){
        val transaction = supportFragmentManager.beginTransaction()
        entries.forEach {e ->
            val parametros = Bundle()
            parametros.putInt("id", e.id!!)
            parametros.putString("name", e.name)
            parametros.putInt("category", e.idReceipeCategory!!)
            parametros.putFloat("value", e.value!!)
            val listEntryFrag = ListEntry()
            listEntryFrag.arguments = parametros
            transaction.add(R.id.modalEntryFrag, listEntryFrag)
        }
        transaction.commit()
    }
}
