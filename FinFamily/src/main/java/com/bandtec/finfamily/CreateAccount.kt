package com.bandtec.finfamily

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bandtec.finfamily.utils.*
import kotlinx.android.synthetic.main.activity_create_account.*
import java.text.SimpleDateFormat

class CreateAccount : AppCompatActivity() {

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        val spCreate1 : SharedPreferences = getSharedPreferences("spCreate1", Context.MODE_PRIVATE)

        inputcpf.addTextChangedListener(MaskEditUtil.mask(inputcpf, MaskEditUtil.FORMAT_CPF))
        inputdatebirth.addTextChangedListener(MaskEditUtil.mask(inputdatebirth, MaskEditUtil.FORMAT_DATE))

        buttonnext.setOnClickListener {
            val intent = Intent(this, CreateAccount2::class.java)


            val fullName = inputname.text.toString()
            val cpf = MaskEditUtil.unmask(inputcpf.text.toString())
            val birthday = inputdatebirth.text.toString()

            if (fullName.isEmpty()) {
                inputname.error = "Nome Completo é um campo obrigatório!"
                inputname.requestFocus()
                return@setOnClickListener
            }else{
                if(!MaskEditUtil.validateFullName(fullName)){
                    inputname.error = "Nome Completo é inválido!"
                    inputname.requestFocus()
                    return@setOnClickListener
                }
            }

            if (cpf.isEmpty()) {
                inputcpf.error = "CPF é um campo obrigatório!"
                inputcpf.requestFocus()
                return@setOnClickListener
            }else{
                if(!CpfValidator.isCPF(cpf)){
                    inputcpf.error = "CPF não é válido!"
                    inputcpf.requestFocus()
                    return@setOnClickListener
                }
            }

            if (birthday.isEmpty()) {
                inputdatebirth.error = "Data de nascimento é um campo obrigatório!"
                inputdatebirth.requestFocus()
                return@setOnClickListener
            }else{
                if(!DateValidation.isValid(birthday)){
                    inputdatebirth.error = "Data de nascimento inválida!"
                    inputdatebirth.requestFocus()
                    return@setOnClickListener
                }
            }

            spCreate1.edit().putString("full_name", fullName).apply()
            spCreate1.edit().putString("cpf", cpf).apply()
            spCreate1.edit().putString("birthday", birthday).apply()

            // start your next activity
            startActivity(intent)
        }
    }
}
