package com.bandtec.finfamily.api

import com.bandtec.finfamily.model.*
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @POST("users/login")
    fun loginUser(@Body user:CredencialsModel):Call<UserResponse>

    @POST("users/update/{id}")
    fun updateUser(@Body user:UpdateUserModel, @Path("id") userId: Int):Call<UserResponse>

    @POST("users")
    fun signupUser (@Body user:UserResponse):Call<UserResponse>

    @POST("groups/create")
    fun createGroup (@Body group:CreateGroupModel):Call<GroupResponse>

    @GET("users/{userId}/groups")
    fun getUserGroups(@Path("userId") userId: Int):Call<List<GroupResponse>>

    @POST("groups/participants/add/{userId}/{externalId}")
    fun addGroupMember(
        @Path("userId") userId: Int,
        @Path("externalId") externalGroupId : String
    ): Call<GroupsResponse>

    @GET("transactions/{groupId}/entries")
    fun getTransactions(
        @Path("groupId") groupId : Int
    ): Call<List<GroupTransResponse>>
}

