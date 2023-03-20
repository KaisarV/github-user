package com.kai.githubuser.retrofit

import com.kai.githubuser.response.UserResponse
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
    ): Call<UserResponse>
}