package com.bandtec.finfamily.popups

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bandtec.finfamily.Group
import com.bandtec.finfamily.R
import com.bandtec.finfamily.api.RetrofitClient
import kotlinx.android.synthetic.main.activity_pop_delete_put.*
import kotlinx.android.synthetic.main.activity_pop_new_invoice.*
import kotlinx.android.synthetic.main.activity_pop_new_invoice.btnClose
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopDeleteGoal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_delete_goal)

        val goalId = intent?.extras?.getInt("goalId", 0)

        btnClose.setOnClickListener {
            finish()
        }

        btNo.setOnClickListener {
            finish()
        }

        btYes.setOnClickListener {
            deleteExpense(goalId!!)
            finish()
        }
    }

    fun deleteExpense(goalId: Int) {
        RetrofitClient.instance.removeGoals(goalId)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.default_error),
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.code().toString() == "200") {
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.goal_removed),
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.default_error),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            })
    }
}
