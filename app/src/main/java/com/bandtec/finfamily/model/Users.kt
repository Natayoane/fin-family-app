package com.bandtec.finfamily.model

import com.google.gson.annotations.SerializedName
import java.sql.Date
import java.sql.Timestamp

data class Users (
    @SerializedName("id")
    var id : Int,
    @SerializedName("full_name")
    var full_name : String,
    @SerializedName("cpf")
    var cpf : String,
    @SerializedName("birthday")
    var birthday : Date,
    @SerializedName("phone_area_code")
    var phoneAreaCode : String,
    @SerializedName("phone_area_number")
    var phoneAreaNumber : String,
    @SerializedName("email")
    var email : String,
    @SerializedName("pasword")
    var password : String,
    @SerializedName("create_at")
    var createdAt : Timestamp,
    @SerializedName("updated_at")
    var updatedAt : Timestamp,
    @SerializedName("nickname")
    var nickname : String

)