package com.kai.githubuser.retrofit

import com.kai.githubuser.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("/search/users")
    fun getUsers(
        @Query("q") name: String
    ):  Call<UserResponse>

    @GET("users/{login}")
    fun getDetailUser(
        @Path("login") login: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<FollowResponseItem>>

//    @GET("users/{username}/following")
//    fun getFollowing(
//        @Path("username") username: String
//    ): Call<FollowerFollowingResponse>




}