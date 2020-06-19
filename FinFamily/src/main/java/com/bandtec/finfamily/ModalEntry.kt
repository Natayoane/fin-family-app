package com.bandtec.finfamily

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.fragments.ListEntry
import com.bandtec.finfamily.model.GroupTransResponse
import kotlinx.android.synthetic.main.activity_group.*
import kotlinx.android.synthetic.main.activity_modal_entry.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModalEntry : AppCompatActivity() {
    var fragSize = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modal_entry)
        val groupId = intent.extras?.get("groupId").toString().toInt()
        val month = intent.extras?.get("month").toString()
        getEntries(groupId, month)

        modalEntryRefresh.setOnRefreshListener {
            val frags = supportFragmentManager
            var i = 0
            while (i < fragSize) {
                val fragment = frags.findFragmentByTag("entries$i")
                frags.beginTransaction().detach(fragment!!).commit()
                i++
            }
            getEntries(groupId, month)
        }

        btnClose.setOnClickListener {
            finish()
        }
    }

    fun getEntries(groupId: Int, month : String) {
        modalEntryRefresh.isRefreshing = true

        RetrofitClient.instance.getEntries(groupId, month)
            .enqueue(object : Callback<List<GroupTransResponse>> {
                override fun onFailure(call: Call<List<GroupTransResponse>>, t: Throwable) {
                    modalEntryRefresh.isRefreshing = false
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<List<GroupTransResponse>>,
                    response: Response<List<GroupTransResponse>>
                ) {
                    modalEntryRefresh.isRefreshing = false
                    if (response.code().toString() == "200") {
                        setEntries(response.body()!!)
                    } else {
                        println("Something are wrong!")
                    }
                }
            })
    }

    fun setEntries(entries: List<GroupTransResponse>) {
        val transaction = supportFragmentManager.beginTransaction()
        entries.forEachIndexed {i, e ->
            val parametros = Bundle()
            parametros.putInt("id", e.id!!)
            parametros.putString("name", e.name)
            parametros.putInt("category", e.idReceipeCategory!!)
            parametros.putFloat("value", e.value!!)
            val listEntryFrag = ListEntry()
            listEntryFrag.arguments = parametros
            transaction.add(R.id.modalEntryFrag, listEntryFrag, "entries$i")
        }
        transaction.commit()
        fragSize = entries.size

    }
}
