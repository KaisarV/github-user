package com.kai.githubuser.retrofit

import com.kai.githubuser.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("/search/users")
    fun getUsers(
        @Query("q") name: String,
        @Header("Authorization") token: String
    ):  Call<UserResponse>

    @GET("users/{login}")
    fun getDetailUser(
        @Path("login") login: String,
        @Header("Authorization") token: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")

    fun getFollower(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): Call<List<FollowResponseItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): Call<List<FollowResponseItem>>




}