package com.bandtec.finfamily

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bandtec.finfamily.utils.MaskEditUtil
import kotlinx.android.synthetic.main.activity_create_account2.*

class CreateAccount2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account2)

        val spCreate2 : SharedPreferences = getSharedPreferences("spCreate1", Context.MODE_PRIVATE)
        val intent = Intent(this, CreateAccount3::class.java)

        buttonnext2.setOnClickListener {

            val email = inputcadastroemail.text.toString()
            val password = inputpassword.text.toString()
            val passwordConfirm = inputverificapassword.text.toString()

            if (email.isEmpty()) {
                inputcadastroemail.error = getString(R.string.email_validation_input)
                inputcadastroemail.requestFocus()
                return@setOnClickListener
            }else {
                if (!MaskEditUtil.validateEmail(email)) {
                    inputcadastroemail.error = getString(R.string.invalid_mail)
                    inputcadastroemail.requestFocus()
                    return@setOnClickListener
                }
            }

            if (password.isEmpty()) {
                inputpassword.error = getString(R.string.password_validation_input)
                inputpassword.requestFocus()
                return@setOnClickListener
            }else{
                if(password.length < 8){
                    inputpassword.error = getString(R.string.invalid_password)
                    inputpassword.requestFocus()
                    return@setOnClickListener
                }
                if(!MaskEditUtil.validatePassword(password)){
                    inputpassword.error = getString(R.string.invalid_password)
                    inputpassword.requestFocus()
                    return@setOnClickListener
                }
            }

            if (passwordConfirm.isEmpty()) {
                inputverificapassword.error = getString(R.string.password_confirmation_input)
                inputverificapassword.requestFocus()
                return@setOnClickListener
            }else {
                if (password != passwordConfirm) {
                    inputpassword.error = getString(R.string.invalid_password_confirmation)
                    inputverificapassword.error = getString(R.string.invalid_password_confirmation)
                    return@setOnClickListener

                }
            }

            spCreate2.edit().putString("email", email).apply()
            spCreate2.edit().putString("password", password).apply()

            startActivity(intent)
        }
    }
}
