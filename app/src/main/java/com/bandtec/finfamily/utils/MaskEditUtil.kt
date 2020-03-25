package com.bandtec.finfamily.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.util.regex.Matcher
import java.util.regex.Pattern


object MaskEditUtil {
    const val FORMAT_CPF = "###.###.###-##"
    const val FORMAT_FONE_AREA_CODE = "(##)"
    const val FORMAT_FONE_AREA_NUMBER = "#####-####"
    const val FORMAT_DATE = "##/##/####"
    private const val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
    private const val EMAIL_ADDRESS_PATTERN = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
    private const val FULL_NAME_PATTERN = "^[A-Z][a-z]+\\s[A-Z][a-z]+\$"

    fun validatePassword(password : String) : Boolean{
        val pattern : Pattern = Pattern.compile(PASSWORD_PATTERN)
        val matcher = pattern.matcher(password)

        return matcher.matches()
    }

    fun validateEmail(email : String) : Boolean{
        val pattern : Pattern = Pattern.compile(EMAIL_ADDRESS_PATTERN)
        val matcher = pattern.matcher(email)

        return matcher.matches()
    }

    fun validateFullName(fullName : String) : Boolean{
        val pattern : Pattern = Pattern.compile(FULL_NAME_PATTERN)
        val matcher = pattern.matcher(fullName)

        return matcher.matches()
    }

    fun mask(ediTxt: EditText, mask: String): TextWatcher {
        return object : TextWatcher {
            var isUpdating = false
            var old = ""
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                val str = unmask(s.toString())
                var mascara = ""
                if (isUpdating) {
                    old = str
                    isUpdating = false
                    return
                }
                var i = 0
                for (m in mask.toCharArray()) {
                    if (m != '#' && str.length > old.length) {
                        mascara += m
                        continue
                    }
                    mascara += try {
                        str[i]
                    } catch (e: Exception) {
                        break
                    }
                    i++
                }
                isUpdating = true
                ediTxt.setText(mascara)
                ediTxt.setSelection(mascara.length)
            }
        }
    }

    fun unmask(s: String): String {
        return s.replace("[.]".toRegex(), "").replace("[-]".toRegex(), "")
            .replace("[/]".toRegex(), "").replace("[(]".toRegex(), "").replace("[ ]".toRegex(), "")
            .replace("[:]".toRegex(), "").replace("[)]".toRegex(), "")
    }
}
