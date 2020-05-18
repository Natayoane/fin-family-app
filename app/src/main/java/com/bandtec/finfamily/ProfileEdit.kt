package com.bandtec.finfamily

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bandtec.finfamily.Popups.PopConfirmAction
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.model.UserResponse
import com.bandtec.finfamily.utils.MaskEditUtil
import kotlinx.android.synthetic.main.activity_profile_edit.*
import kotlinx.android.synthetic.main.activity_profile_edit.etEmail
import kotlinx.android.synthetic.main.activity_profile_edit.etName
import kotlinx.android.synthetic.main.activity_profile_edit.etNickname
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileEdit : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)
        val intent = Intent(this, Panel::class.java)
        val sp: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        val userId = sp.getInt("id", 0)


        etName.hint = sp.getString("full_name", "")
        etNickname.hint = sp.getString("nickname", "")
        etEmail.hint = sp.getString("email", "")

        btnDeleteAccount.setOnClickListener(){
            val delete = Intent(this,
                PopConfirmAction::class.java)

            delete.putExtra("choose", 0)

            startActivity(delete)
        }

        btnSaveProfile.setOnClickListener {
            val fullName = etName.text.toString()
            val nickname = etNickname.text.toString()
            val email = etEmail.text.toString()
            val basePassword = etBasePassword.text.toString()
            val newPassword = etNewPassword.text.toString()
            val passwordConfirm = etPasswordConfirm.text.toString()

            if (basePassword.isNotEmpty()) {
                if (newPassword != passwordConfirm) {
                    etNewPassword.error = "As senhas devem ser iguais!"
                    etNewPassword.requestFocus()
                    return@setOnClickListener
                } else {
                    if (newPassword.length < 8) {
                        etNewPassword.error =
                            "A senha deve conter entre 8 e 60 caracteres contendo ao " +
                                    "menos uma letra maiúscula, um número e um caracter especial!"
                        etNewPassword.requestFocus()
                        return@setOnClickListener
                    }
                    if (!MaskEditUtil.validatePassword(newPassword)) {
                        etNewPassword.error =
                            "A senha deve conter entre 8 e 60 caracteres contendo ao " +
                                    "menos uma letra maiúscula, um número e um caracter especial!"
                        etNewPassword.requestFocus()
                        return@setOnClickListener
                    }
                }
            } else if (basePassword.isEmpty() && newPassword.isNotEmpty() || passwordConfirm.isNotEmpty()) {
                etBasePassword.error = "É necessário digitar sua senha atual para altera sua senha!"
                etBasePassword.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.updateUser(
                fullName,
                nickname,
                email,
                basePassword,
                newPassword,
                userId
            )
                .enqueue(object : Callback<UserResponse> {
                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>
                    ) {
                        when {
                            response.code().toString() == "200" -> {

                                Toast.makeText(
                                    applicationContext,
                                    "Dado(s) alterado(s) com sucesso!",
                                    Toast.LENGTH_LONG
                                ).show()
                                sp.edit().putString("full_name", response.body()?.fullName).apply()
                                sp.edit().putString("email", response.body()?.email).apply()
                                sp.edit().putString("nickname", response.body()?.nickname).apply()
                                startActivity(intent)
                            }
                            response.code().toString() == "401" -> {
                                etBasePassword.error = "A senha informada está incorreta"
                                etBasePassword.requestFocus()
                            }
                            else -> {
                                Toast.makeText(
                                    applicationContext,
                                    "Erro interno no servidor! Por favor, tente novamente mais tarde!",
                                    Toast.LENGTH_LONG
                                ).show()
                                startActivity(intent)

                            }
                        }
                    }
                })
            }
        }
    }

