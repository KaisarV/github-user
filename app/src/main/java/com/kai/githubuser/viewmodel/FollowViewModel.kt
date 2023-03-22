package com.kai.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kai.githubuser.response.*
import com.kai.githubuser.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel: ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listFollower = MutableLiveData<List<FollowResponseItem>>()
    val listFollower: LiveData<List<FollowResponseItem>> = _listFollower

    private val _listFollowing = MutableLiveData<List<FollowResponseItem>>()
    val listFollowing: LiveData<List<FollowResponseItem>> = _listFollowing

    companion object{
        const val TAG = "FollowerViewModel2"
    }

    fun getFollower(login: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollower(login, "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")
        client.enqueue(object : Callback<List<FollowResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowResponseItem>>,
                response: Response<List<FollowResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listFollower.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure2: ${t.message}")
            }
        })
    }

    fun getFollowing(login:String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(login, "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")
        client.enqueue(object : Callback<List<FollowResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowResponseItem>>,
                response: Response<List<FollowResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listFollowing.value = response.body()

                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure2: ${t.message}")
            }
        })
    }

}