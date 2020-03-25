package com.bandtec.finfamily.utils

import java.text.SimpleDateFormat
import java.time.Year
import java.util.*

object BirthdayValidator {

    fun isValid(birthday : String) : Boolean{

        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val date = sdf.parse(birthday)
        val currentDate = Date()



        return !currentDate.before(date)

    }
}