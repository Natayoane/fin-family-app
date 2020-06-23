package com.bandtec.finfamily

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.model.CredencialsModel
import com.bandtec.finfamily.model.UserResponse
import com.bandtec.finfamily.utils.MaskEditUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val group = Intent(this, Group::class.java)

        inputemail.requestFocus()

        val sp: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)

        buttonlogin.setOnClickListener {

            val email = inputemail.text.toString()
            val password = inputpassword.text.toString()

            if (email.isEmpty()) {
                inputemail.error = getString(R.string.email_validation_input)
                inputemail.requestFocus()
                return@setOnClickListener
            } else {
                if (!MaskEditUtil.validateEmail(email)) {
                    inputemail.error = getString(R.string.invalid_mail)
                    inputemail.requestFocus()
                    return@setOnClickListener
                }
            }

            if (password.isEmpty()) {
                inputpassword.error = getString(R.string.password_validation_input)
                inputpassword.requestFocus()
                return@setOnClickListener
            }

            val credentials = CredencialsModel(email, password)
            RetrofitClient.instance.loginUser(credentials)
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
                        if (response.code().toString() == "200") {
                            sp.edit().putBoolean("logged", true).apply()
                            sp.edit().putInt("userId", response.body()?.id!!).apply()
                            sp.edit().putString("full_name", response.body()?.fullName).apply()
                            sp.edit().putString("email", response.body()?.email).apply()
                            sp.edit().putString("nickname", response.body()?.nickname).apply()
                            startActivity(group)
                            finish()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                getString(R.string.invalid_credentials),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                })
        }
    }
}
