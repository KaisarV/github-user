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

    companion object{
        const val TAG = "FollowerViewModel2"
    }

    init {
        getFollower()
    }

    private fun getFollower() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers("sidiqpermana")
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
                        for(a in _listFollower.value!!){
                            Log.d("CEKK", a.login)
                        }
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