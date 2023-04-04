package com.kai.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kai.githubuser.adapter.FavoriteUserAdapter
import com.kai.githubuser.databinding.ActivityFavoriteBinding
import com.kai.githubuser.helper.ViewModelFactory
import com.kai.githubuser.viewmodel.FavoriteUserViewModel

class FavoriteActivity : AppCompatActivity() {

    private var _activityFavoriteBinding: ActivityFavoriteBinding? = null
    private val binding get() = _activityFavoriteBinding

    private lateinit var adapter: FavoriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val favoriteViewModel = obtainViewModel(this@FavoriteActivity)

        favoriteViewModel.getAllNotes().observe(this) { favoriteUserList ->
            if (favoriteUserList != null) {
                adapter.setListFavoriteUser(favoriteUserList)
            }
        }

        adapter = FavoriteUserAdapter()

        binding?.rvUser?.layoutManager = LinearLayoutManager(this)
        binding?.rvUser?.setHasFixedSize(true)
        binding?.rvUser?.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteUserViewModel::class.java)
    }
}