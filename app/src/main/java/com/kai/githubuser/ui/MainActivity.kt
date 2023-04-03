package com.kai.githubuser.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kai.githubuser.R
import com.kai.githubuser.adapter.UserAdapter
import com.kai.githubuser.databinding.ActivityMainBinding
import com.kai.githubuser.helper.ViewModelFactory
import com.kai.githubuser.response.ItemsItem
import com.kai.githubuser.viewmodel.FavoriteUserViewModel
import com.kai.githubuser.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        val mainViewModel = obtainViewModel(this@MainActivity)
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

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel?.getUser(query)
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> {
                finishAffinity()
            }
            R.id.favorite -> {
                Toast.makeText(this, "Ke menu fav", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
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
                moveDetailUser(data.login)
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

    fun moveDetailUser(user_login: String) {
        val moveWithObjectIntent = Intent(this@MainActivity, DetailUserActivity::class.java)

        moveWithObjectIntent.putExtra(DetailUserActivity.LOGIN, user_login)
        moveWithObjectIntent.putExtra(FollowFragment.LOGIN, user_login)
        startActivity(moveWithObjectIntent)
    }
}