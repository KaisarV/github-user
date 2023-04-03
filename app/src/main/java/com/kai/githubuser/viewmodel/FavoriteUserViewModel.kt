package com.kai.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kai.githubuser.database.FavoriteUser
import com.kai.githubuser.repository.FavoriteUserRepository

class FavoriteUserViewModel (application: Application) : ViewModel() {
    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)
    fun getAllNotes(): LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getAllFavoriteUser()
}