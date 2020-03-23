package com.bandtec.finfamily.model

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.Date
import java.time.LocalDateTime

data class Users(
    @SerializedName("full_name")
    var fullName: String?,
    @SerializedName("cpf")
    var cpf: String?,
    @SerializedName("birthday")
    var birthday: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("password")
    var password: String?,
    @SerializedName("nickname")
    var nickname: String?,
    @SerializedName("phone_area_code")
    var phoneAreaCode: String?,
    @SerializedName("phone_area_number")
    var phoneAreaNumber: String?

)