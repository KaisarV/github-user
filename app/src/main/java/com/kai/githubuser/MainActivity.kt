package com.kai.githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kai.githubuser.adapter.UserAdapter
import com.kai.githubuser.databinding.ActivityMainBinding
import com.kai.githubuser.response.ItemsItem
import com.kai.githubuser.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        mainViewModel.listUser.observe(this) { users ->
            setUserData(users)
        }

        binding.btnSearch.setOnClickListener {
            Toast.makeText(this, binding.edSearch.text, Toast.LENGTH_SHORT).show()
            mainViewModel.getUser(binding.edSearch.text.toString())
        }

    }



    private fun setUserData(users: List<ItemsItem>) {
        val listUser = ArrayList<ItemsItem>()
        for (user in users) {
            listUser.add(
                user
            )
        }

        val listUserAdapter = UserAdapter(listUser)
        listUserAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                moveProfile(data)
            }
        })
        binding.rvUser.adapter = listUserAdapter
        binding.edSearch.setText("")
    }


   private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


    private fun moveProfile(user: ItemsItem?) {
        val moveWithObjectIntent = Intent(this@MainActivity, DetailUserActivity::class.java)
        moveWithObjectIntent.putExtra(DetailUserActivity.LOGIN, user?.login)
        startActivity(moveWithObjectIntent)
    }
}