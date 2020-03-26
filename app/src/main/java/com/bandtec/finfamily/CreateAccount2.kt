package com.bandtec.finfamily

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
                inputcadastroemail.error = "Email é um campo obrigatório!"
                inputcadastroemail.requestFocus()
                return@setOnClickListener
            }else {
                if (!MaskEditUtil.validateEmail(email)) {
                    inputcadastroemail.error = "Email inválido!"
                    inputcadastroemail.requestFocus()
                    return@setOnClickListener
                }
            }

            if (password.isEmpty()) {
                inputpassword.error = "Senha é um campo obrigatório!"
                inputpassword.requestFocus()
                return@setOnClickListener
            }else{
                if(password.length < 8){
                    inputpassword.error = "A senha deve conter entre 8 e 60 caracteres contendo ao " +
                            "menos uma letra maiúscula, um número e um caracter especial!"
                    inputpassword.requestFocus()
                    return@setOnClickListener
                }
                if(!MaskEditUtil.validatePassword(password)){
                    inputpassword.error = "A senha deve conter entre 8 e 60 caracteres contendo ao " +
                            "menos uma letra maiúscula, um número e um caracter especial!"
                    inputpassword.requestFocus()
                    return@setOnClickListener
                }
            }

            if (passwordConfirm.isEmpty()) {
                inputverificapassword.error = "Confirmação de Senha é um campo obrigatório!"
                inputverificapassword.requestFocus()
                return@setOnClickListener
            }else {
                if (password != passwordConfirm) {
                    inputpassword.error = "As senhas devem ser iguais!"
                    inputverificapassword.error = "As senhas devem ser iguais!"
                    return@setOnClickListener

                }
            }

            spCreate2.edit().putString("email", email).apply()
            spCreate2.edit().putString("password", password).apply()

            startActivity(intent)
        }
    }
}
