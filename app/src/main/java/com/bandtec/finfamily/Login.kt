package com.bandtec.finfamily

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val intent = Intent(this, Group::class.java)

        buttonlogin.setOnClickListener {
            // start your next activity
        inputemail.requestFocus()

        val sp : SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)

        buttonlogin.setOnClickListener {

            val email = inputemail.text.toString()
            val password = inputpassword.text.toString()

            if (email.isEmpty()) {
                inputemail.error = "Email is required!"
                inputemail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                inputpassword.error = "Password is required!"
                inputpassword.requestFocus()
                return@setOnClickListener
            }


            RetrofitClient.instance.loginUser(email, password)
                .enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if(response.code().toString() == "200"){
//                            Toast.makeText(applicationContext, response.body()?.fullName, Toast.LENGTH_LONG).show()
                            Toast.makeText(applicationContext, response.code().toString(), Toast.LENGTH_LONG).show()
                            startActivity(intent)
                            sp.edit().putBoolean("logged", true).apply()
                            sp.edit().putInt("id", response.body()?.id!!).apply()
                            sp.edit().putString("full_name", response.body()?.fullName).apply()
                            sp.edit().putString("email", response.body()?.email).apply()
                            sp.edit().putString("nickname", response.body()?.nickname).apply()

                        }
                        else {
                            Toast.makeText(applicationContext, "User and/or password are incorrect!", Toast.LENGTH_LONG).show()
                        }


                    }

                })
            }
        }
    }
}
