package com.kai.githubuser.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kai.githubuser.R
import com.kai.githubuser.adapter.SectionsPagerAdapter
import com.kai.githubuser.database.FavoriteUser
import com.kai.githubuser.databinding.ActivityDetailUserBinding
import com.kai.githubuser.helper.ViewModelFactory
import com.kai.githubuser.response.UserDetailResponse
import com.kai.githubuser.viewmodel.DetailUserViewModel
import com.kai.githubuser.viewmodel.FavoriteUserViewModel

class DetailUserActivity : AppCompatActivity() {
    companion object {

        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

        const val LOGIN = "login"
    }

    private lateinit var binding: ActivityDetailUserBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val login = intent.getStringExtra(LOGIN)

        val detailUserViewModel = obtainViewModel(this@DetailUserActivity)

        if (login != null) {
            detailUserViewModel.getDetailUser(login)
        }

        detailUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailUserViewModel.detailUser.observe(this) { details->
            setUserData(details)

            binding.addFav.setOnClickListener{
                val favUser =
                    details.login?.let { it1 -> FavoriteUser(it1, details.avatarUrl, details.url) }
                if (favUser != null) {
                    detailUserViewModel.insert(favUser)
                }
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailUserViewModel::class.java]
    }

    @SuppressLint("SetTextI18n")
    private fun setUserData(userDetail: UserDetailResponse) {
        binding.loginName.text = userDetail.login
        binding.name.text = userDetail.name
        binding.publicRepos.text = userDetail.publicRepos.toString() + " Public Repos"

        val follow = arrayOf(
            userDetail.followers.toString(),
            userDetail.following.toString()
        )

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = follow[position]+" " + resources.getString(TAB_TITLES[position])
        }.attach()

        Glide.with(this)
            .load(userDetail.avatarUrl)
            .into(binding.profile)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


}