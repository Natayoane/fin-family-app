package com.bandtec.finfamily.api

import com.bandtec.finfamily.model.LoginResponse
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @FormUrlEncoded
    @POST("login")
    fun loginUser(
        @Field("email") email:String,
        @Field("password") password:String
    ):Call<LoginResponse>
}