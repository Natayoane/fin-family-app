package com.bandtec.finfamily

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.model.UserResponse
import com.bandtec.finfamily.utils.MaskEditUtil
import kotlinx.android.synthetic.main.activity_create_account3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CreateAccount3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account3)

        val spCreate3: SharedPreferences = getSharedPreferences("spCreate1", Context.MODE_PRIVATE)
        val home = Intent(this, MainActivity::class.java)

        inputcode.addTextChangedListener(
            MaskEditUtil.mask(
                inputcode,
                MaskEditUtil.FORMAT_FONE_AREA_CODE
            )
        )
        inputnumber.addTextChangedListener(
            MaskEditUtil.mask(
                inputnumber,
                MaskEditUtil.FORMAT_FONE_AREA_NUMBER
            )
        )


        buttonnext3.setOnClickListener {

            val fullName = spCreate3.getString("full_name", "")
            val cpf = spCreate3.getString("cpf", "")
            val birthday = spCreate3.getString("birthday", "")
            val email = spCreate3.getString("email", "")
            val password = spCreate3.getString("password", "")
            val nickname = inputcadastronick.text.toString()
            val phoneAreaCode = MaskEditUtil.unmask(inputcode.text.toString())
            val phoneAreaNumber = MaskEditUtil.unmask(inputnumber.text.toString())

            if (nickname.isEmpty()) {
                inputcadastronick.error = getString(R.string.nickname_validation_input)
                inputcadastronick.requestFocus()
                return@setOnClickListener
            }

            if (phoneAreaCode.isEmpty()) {
                inputcode.error = getString(R.string.code_validation_input)
                inputcode.requestFocus()
                return@setOnClickListener
            }

            if (phoneAreaNumber.isEmpty()) {
                inputnumber.error = getString(R.string.phone_validation_input)
                inputnumber.requestFocus()
                return@setOnClickListener
            }

            val user = UserResponse(
                null,
                fullName,
                cpf,
                birthday,
                phoneAreaCode,
                phoneAreaNumber,
                email,
                password,
                null,
                null,
                nickname
            )

            RetrofitClient.instance.signupUser(user)
                .enqueue(object : Callback<UserResponse> {
                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.default_error),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>
                    ) {
                        if (response.code().toString() == "201") {
                            Toast.makeText(
                                applicationContext,
                                getString(R.string.account_successful_created),
                                Toast.LENGTH_LONG
                            ).show()
                            startActivity(home)
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
}
