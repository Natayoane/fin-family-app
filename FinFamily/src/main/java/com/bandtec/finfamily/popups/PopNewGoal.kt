package com.bandtec.finfamily.popups

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bandtec.finfamily.R
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.model.GoalsResponse
import com.bandtec.finfamily.utils.MaskEditUtil
import kotlinx.android.synthetic.main.activity_pop_new_goal.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopNewGoal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_new_goal)
        val groupId = intent.extras?.get("groupId").toString().toInt()
        etDate.addTextChangedListener(MaskEditUtil.mask(etDate, MaskEditUtil.FORMAT_DATE))

        btnClose.setOnClickListener {
            finish()
        }

        btSaveGoal.setOnClickListener {
            val name = etName.text.toString()
            val description = etDescription.text.toString()
            val goalValue = etValue.text.toString()
            val deadline = etDate.text.toString()

            if(name.isEmpty()){
                etName.error = getString(R.string.goal_name)
                etName.requestFocus()
                return@setOnClickListener
            }

            if(description.isEmpty()){
                etDescription.error = getString(R.string.goal_description)
                etDescription.requestFocus()
                return@setOnClickListener
            }

            if(goalValue.isEmpty()){
                etName.error = getString(R.string.goal_value)
                etName.requestFocus()
                return@setOnClickListener
            }

            if(deadline.isEmpty()){
                etDate.error = getString(R.string.goal_deadline)
                etDate.requestFocus()
                return@setOnClickListener
            }
            val goal = GoalsResponse(null, name, description, goalValue.toFloat(), deadline, groupId)
            createGoal(goal)
            setResult(RESULT_OK)
            finish()
        }
    }

    fun createGoal(goal: GoalsResponse) {
        RetrofitClient.instance.createGoals(goal)
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
                                getString(R.string.goal_created),
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
}