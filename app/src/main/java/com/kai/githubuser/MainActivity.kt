package com.kai.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kai.githubuser.databinding.ActivityMainBinding
import com.kai.githubuser.response.ItemsItem
import com.kai.githubuser.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val TAG = "MainActivity"
        private const val USERNAME = "ARIF"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        supportActionBar?.hide()
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)
        findUser()
    }

    private fun findUser() {
        showLoading(true)
        val client = ApiConfig.getApiService().getUsers(USERNAME)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
//                        setRestaurantData(responseBody.restaurant)
                        setUserData(responseBody.items)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
//    private fun setRestaurantData(restaurant: Restaurant) {
//        binding.tvTitle.text = restaurant.name
//        binding.tvDescription.text = restaurant.description
//        Glide.with(this@MainActivity)
//            .load("https://restaurant-api.dicoding.dev/images/large/${restaurant.pictureId}")
//            .into(binding.ivPicture)
//    }
    private fun setUserData(consumerReviews: List<ItemsItem>) {
        val listUser = ArrayList<String>()
        for (user in consumerReviews) {
            listUser.add(
                """
                ${user.login}
                - ${user.url}
                """.trimIndent()
            )
        }

        val adapter = UserAdapter(listUser)
        binding.rvUser.adapter = adapter
        binding.edSearch.setText("")
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}