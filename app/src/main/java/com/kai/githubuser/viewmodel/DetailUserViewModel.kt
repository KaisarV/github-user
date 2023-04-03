package com.kai.githubuser.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kai.githubuser.database.FavoriteUser
import com.kai.githubuser.repository.FavoriteUserRepository
import com.kai.githubuser.response.UserDetailResponse
import com.kai.githubuser.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailUser = MutableLiveData<UserDetailResponse>()
    val detailUser: LiveData<UserDetailResponse> = _detailUser


    fun getDetailUser(login_name: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(login_name, "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")
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detailUser.value = response.body()
                    }
                } else {
                    Log.e(MainViewModel.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(MainViewModel.TAG, "onFailure: ${t.message}")
            }


        })
    }

    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun count(login_name: String): LiveData<Int> = mFavoriteUserRepository.count(login_name)

    fun insert(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.insert(favoriteUser)
    }

    fun delete(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.delete(favoriteUser)
    }
}

