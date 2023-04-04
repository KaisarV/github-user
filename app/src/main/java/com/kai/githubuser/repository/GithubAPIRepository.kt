package com.kai.githubuser.repository

import android.app.Application
import com.kai.githubuser.database.FavoriteUserDao
import com.kai.githubuser.database.FavoriteUserRoomDatabase
import com.kai.githubuser.response.UserResponse
import com.kai.githubuser.retrofit.ApiConfig
import retrofit2.Call

class GithubAPIRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao
    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getUser(username : String): Call<UserResponse> =
        ApiConfig.getApiService().getUsers(username, "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")

}