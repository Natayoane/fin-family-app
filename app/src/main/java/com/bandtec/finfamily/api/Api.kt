package com.bandtec.finfamily.api

import com.bandtec.finfamily.model.LoginResponse
import com.bandtec.finfamily.model.SignupResponse
import com.bandtec.finfamily.model.Users
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @FormUrlEncoded
    @POST("user/login")
    fun loginUser(
        @Field("email") email:String,
        @Field("password") password:String
    ):Call<LoginResponse>

    @FormUrlEncoded
    @POST("user")
    fun signupUser (
        @Field("fullName") fullName:String,
        @Field("cpf") cpf:String,
        @Field("birthday") birthday:String,
        @Field("email") email:String,
        @Field("password") password:String,
        @Field("nickname") nickname:String,
        @Field("phoneAreaCode") phoneAreaCode:String,
        @Field("phoneAreaNumber") phoneAreaNumber:String
    ):Call<LoginResponse>
}