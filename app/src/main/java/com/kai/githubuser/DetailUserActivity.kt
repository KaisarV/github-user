package com.kai.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kai.githubuser.adapter.SectionsPagerAdapter
import com.kai.githubuser.adapter.UserAdapter
import com.kai.githubuser.databinding.ActivityDetailUserBinding
import com.kai.githubuser.databinding.ActivityMainBinding
import com.kai.githubuser.response.ItemsItem
import com.kai.githubuser.response.UserDetailResponse
import com.kai.githubuser.viewmodel.DetailUserViewModel
import com.kai.githubuser.viewmodel.MainViewModel

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

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        val login = intent.getStringExtra(LOGIN)
//        binding.loginName.text = login


        val detailUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailUserViewModel::class.java]

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

    private fun setUserData(userDetail: UserDetailResponse) {
        binding.loginName.text = userDetail.login
        binding.name.text = userDetail.name
        Glide.with(this)
            .load(userDetail.avatarUrl) // URL Gambar
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