package com.bandtec.finfamily

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bandtec.finfamily.api.RetrofitClient
import com.bandtec.finfamily.model.UpdateUserModel
import com.bandtec.finfamily.model.UserResponse
import com.bandtec.finfamily.popups.PopConfirmAction
import com.bandtec.finfamily.utils.MaskEditUtil
import kotlinx.android.synthetic.main.activity_profile_edit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileEdit : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)
        val sp: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        val userId = sp.getInt("userId", 0)
        val btmAvatar = intent?.extras?.getParcelable<Bitmap>("avatar")

        if (btmAvatar != null) {
            img.setImageBitmap(btmAvatar)
        }

        img.setOnClickListener {
            val avatar = Intent(this, Avatar::class.java)
            avatar.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(avatar)
        }

        etName.hint = sp.getString("full_name", "")
        etNickname.hint = sp.getString("nickname", "")
        etEmail.hint = sp.getString("email", "")

        btnDeleteAccount.setOnClickListener {
            val delete = Intent(
                this,
                PopConfirmAction::class.java
            )

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
                    etNewPassword.error = getString(R.string.invalid_password_confirmation)
                    etNewPassword.requestFocus()
                    return@setOnClickListener
                } else {
                    if (newPassword.length < 8) {
                        etNewPassword.error = getString(R.string.password_validation_input)
                        etNewPassword.requestFocus()
                        return@setOnClickListener
                    }
                    if (!MaskEditUtil.validatePassword(newPassword)) {
                        etNewPassword.error = getString(R.string.password_validation_input)
                        etNewPassword.requestFocus()
                        return@setOnClickListener
                    }
                }
            } else if (basePassword.isEmpty() && newPassword.isNotEmpty() || passwordConfirm.isNotEmpty()) {
                etBasePassword.error = getString(R.string.change_password)
                etBasePassword.requestFocus()
                return@setOnClickListener
            }

            val user = UpdateUserModel(fullName, nickname, email, basePassword, newPassword)

            RetrofitClient.instance.updateUser(user, userId)
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
                        when {
                            response.code().toString() == "200" -> {
                                Toast.makeText(
                                    applicationContext,
                                    getString(R.string.successfully_changed_data),
                                    Toast.LENGTH_LONG
                                ).show()
                                sp.edit().putString("full_name", response.body()?.fullName).apply()
                                sp.edit().putString("email", response.body()?.email).apply()
                                sp.edit().putString("nickname", response.body()?.nickname).apply()
                                finish()
                            }
                            response.code().toString() == "401" -> {
                                etBasePassword.error = getString(R.string.invalid_password_change)
                                etBasePassword.requestFocus()
                            }
                            else -> {
                                Toast.makeText(
                                    applicationContext,
                                    getString(R.string.default_error),
                                    Toast.LENGTH_LONG
                                ).show()
                                finish()
                            }
                        }
                    }
                })
        }
    }
}

