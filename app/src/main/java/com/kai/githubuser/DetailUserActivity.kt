package com.kai.githubuser

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kai.githubuser.adapter.SectionsPagerAdapter
import com.kai.githubuser.databinding.ActivityDetailUserBinding
import com.kai.githubuser.response.UserDetailResponse
import com.kai.githubuser.viewmodel.DetailUserViewModel

class DetailUserActivity : AppCompatActivity() {
    companion object {

        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )


        const val LOGIN = "login"
    }

    private lateinit var binding: ActivityDetailUserBinding
    private val detailUserViewModel: DetailUserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val login = intent.getStringExtra(LOGIN)


        if (login != null) {
            detailUserViewModel.getDetailUser(login)
        }

        detailUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailUserViewModel.detailUser.observe(this) { details->
            setUserData(details)
        }
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