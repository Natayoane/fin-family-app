package com.bandtec.finfamily.api

import com.bandtec.finfamily.model.*
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @POST("user/login")
    fun loginUser(@Body user:CredencialsModel):Call<UserResponse>

    @POST("user/update/{id}")
    fun updateUser(@Body user:UpdateUserModel, @Path("id") userId: Int):Call<UserResponse>

    @POST("user")
    fun signupUser (@Body user:UserResponse):Call<UserResponse>

    @POST("group/create")
    fun createGroup (@Body group:CreateGroupModel):Call<GroupResponse>

    @GET("user/{userId}/groups")
    fun getUserGroups(@Path("userId") userId: Int):Call<List<GroupResponse>>

    @POST("group/participants/add/{userId}/{externalId}")
    fun addGroupMember(
        @Path("userId") userId: Int,
        @Path("externalId") externalGroupId : String
    ): Call<GroupParticipantsResponse>

    @GET("transactions/{groupId}")
    fun getTransactions(
        @Path("groupId") groupId : Int
    ): Call<List<GroupTransactionsResponse>>
}

