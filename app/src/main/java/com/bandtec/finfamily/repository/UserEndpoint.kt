package com.bandtec.finfamily.repository

import com.bandtec.finfamily.model.Users
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserEndpoint {

    @FormUrlEncoded
    @POST("api/v1/login")
    fun loginUser(
        @Field("email") email:String,
        @Field("password") password:String
    ):Call<Users>
}