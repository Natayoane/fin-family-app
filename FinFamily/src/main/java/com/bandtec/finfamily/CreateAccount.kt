package com.bandtec.finfamily

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bandtec.finfamily.utils.CpfValidator
import com.bandtec.finfamily.utils.DateValidation
import com.bandtec.finfamily.utils.MaskEditUtil
import kotlinx.android.synthetic.main.activity_create_account.*

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
                inputname.error = getString(R.string.name_validation_input)
                inputname.requestFocus()
                return@setOnClickListener
            }else{
                if(!MaskEditUtil.validateFullName(fullName)){
                    inputname.error = getString(R.string.invalid_full_name)
                    inputname.requestFocus()
                    return@setOnClickListener
                }
            }

            if (cpf.isEmpty()) {
                inputcpf.error = getString(R.string.cpf_validation_input)
                inputcpf.requestFocus()
                return@setOnClickListener
            }else{
                if(!CpfValidator.isCPF(cpf)){
                    inputcpf.error = getString(R.string.invalid_cpf)
                    inputcpf.requestFocus()
                    return@setOnClickListener
                }
            }

            if (birthday.isEmpty()) {
                inputdatebirth.error = getString(R.string.birthday_validation_input)
                inputdatebirth.requestFocus()
                return@setOnClickListener
            }else{
                if(!DateValidation.isValid(birthday)){
                    inputdatebirth.error = getString(R.string.invalid_birthday)
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
