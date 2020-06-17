package com.bandtec.finfamily.popups

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bandtec.finfamily.R
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.model.GoalsResponse
import com.bandtec.finfamily.utils.DateValidation
import com.bandtec.finfamily.utils.MaskEditUtil
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_goals.*
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
                etName.error = "Nome é um campo obrigatório!"
                etName.requestFocus()
                return@setOnClickListener
            }

            if(description.isEmpty()){
                etDescription.error = "Descrição é um campo obrigatório!"
                etDescription.requestFocus()
                return@setOnClickListener
            }

            if(goalValue.isEmpty()){
                etName.error = "Valor é um campo obrigatório!"
                etName.requestFocus()
                return@setOnClickListener
            }

            if(deadline.isEmpty()){
                etDate.error = "Data limite é um campo obrigatório!"
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
                                "Meta criada com sucesso!",
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
}