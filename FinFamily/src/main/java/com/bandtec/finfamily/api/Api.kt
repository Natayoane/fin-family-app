package com.bandtec.finfamily.api

import com.bandtec.finfamily.model.*
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @POST("users/login")
    fun loginUser(@Body user:CredencialsModel):Call<UserResponse>

    @POST("users/update/{id}")
    fun updateUser(@Body user:UpdateUserModel, @Path("id") userId: Int):Call<UserResponse>

    @POST("transactions/create")
    fun createTransaction(@Body transaction : GroupTransResponse) : Call<String>

    @POST("users")
    fun signupUser (@Body user:UserResponse):Call<UserResponse>

    @POST("groups/create")
    fun createGroup (@Body group:CreateGroupModel):Call<GroupResponse>

    @GET("users/{userId}/groups")
    fun getUserGroups(@Path("userId") userId: Int):Call<List<GroupResponse>>

    @POST("groups/participants/add/members/{userId}/{externalId}")
    fun addGroupMember(
        @Path("userId") userId: Int,
        @Path("externalId") externalGroupId : String
    ): Call<String>

    @GET("transactions/{groupId}/entries")
    fun getEntries(
        @Path("groupId") groupId : Int
    ): Call<List<GroupTransResponse>>

    @GET("transactions/{groupId}/{userId}/entries")
    fun getUserEntries(
        @Path("groupId") groupId : Int,
        @Path("userId") userId : Int
    ): Call<List<GroupTransResponse>>

    @GET("transactions/{groupId}/{userId}/entries/total")
    fun getUserEntriesTotal(
        @Path("groupId") groupId : Int,
        @Path("userId") userId : Int
    ): Call<Float>

    @GET("transactions/{groupId}/expenses")
    fun getExpenses(
        @Path("groupId") groupId : Int
    ): Call<List<GroupTransResponse>>

    @GET("groups/participants/members/{externalId}")
    fun getGroupMembers(
        @Path("externalId") extId : String
    ): Call<List<UserResponse>>

    @DELETE("groups/participants/remove/members/{userId}/{groupId}")
    fun leaveGroup(
        @Path("userId") userId : Int,
        @Path("groupId") groupId : Int
    ): Call<Any>
}

