package com.bandtec.finfamily.api

import com.bandtec.finfamily.model.GroupParticipantsResponse
import com.bandtec.finfamily.model.GroupResponse
import com.bandtec.finfamily.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @FormUrlEncoded
    @POST("user/login")
    fun loginUser(
        @Field("email") email:String,
        @Field("password") password:String
    ):Call<UserResponse>

    @FormUrlEncoded
    @POST("user/update/{id}")
    fun updateUser(
        @Field("fullName") fullName:String,
        @Field("nickname") nickname:String,
        @Field("email") email:String,
        @Field("basePassword") basePassword:String,
        @Field("newPassword") newPassword:String,
        @Path("id") userId:Int
    ):Call<UserResponse>

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
    ):Call<UserResponse>

    @FormUrlEncoded
    @POST("group/create")
    fun createGroup (
        @Field("groupName") groupName:String,
        @Field("groupType") groupType:Int,
        @Field("groupOwner") groupOwner:Int
    ):Call<GroupResponse>

    @GET("user/{userId}/groups")
    fun getUserGroups(
        @Path("userId") userId: Int
    ):Call<List<GroupResponse>>

    @POST("group/participants/add/{userId}/{externalId}")
    fun addGroupMember(
        @Path("userId") userId: Int,
        @Path("externalId") externalGroupId : String
    ): Call<GroupParticipantsResponse>
}

