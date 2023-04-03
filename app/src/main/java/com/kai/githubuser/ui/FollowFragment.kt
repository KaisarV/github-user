package com.kai.githubuser.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kai.githubuser.adapter.FollowAdapter
import com.kai.githubuser.databinding.FragmentFollowerBinding
import com.kai.githubuser.response.FollowResponseItem
import com.kai.githubuser.viewmodel.FollowViewModel


class FollowFragment : Fragment() {

    private var binding: FragmentFollowerBinding? = null
    private var recyclerView: RecyclerView? = null
    private val followViewModel: FollowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val login = activity?.intent?.extras?.getString(DetailUserActivity.LOGIN)


        if (login != null) {
            followViewModel.getFollower(login)
            followViewModel.getFollowing(login)
        }

        recyclerView = binding!!.rvUser2
        binding!!.rvUser2.layoutManager = LinearLayoutManager(requireActivity())

        followViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        followViewModel.listFollower.observe(viewLifecycleOwner) { followers ->
            if (followers != null && index == 1){
                setFollow(followers)
            }
        }

        followViewModel.listFollowing.observe(viewLifecycleOwner) { following ->
            if (following != null && index == 2){
                setFollow(following)
            }
        }
    }

    private fun setFollow(users: List<FollowResponseItem>) {
        val listFollowers = ArrayList<FollowResponseItem>()

        for (user in users) {
            listFollowers.add(
                user
            )
        }

        val listFollowerAdapter = FollowAdapter(listFollowers)

        listFollowerAdapter.setOnItemClickCallback(object : FollowAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FollowResponseItem) {
                moveDetailUser(data.login)
                Log.d("Halo", "Halo")
            }
        })

        binding!!.rvUser2.adapter = listFollowerAdapter
    }

    fun moveDetailUser(user_login: String) {
        val moveWithObjectIntent = Intent(activity, DetailUserActivity::class.java)

        moveWithObjectIntent.putExtra(DetailUserActivity.LOGIN, user_login)
        moveWithObjectIntent.putExtra(LOGIN, user_login)
        startActivity(moveWithObjectIntent)
    }

    private fun showLoading(isLoading: Boolean) {

        if (isLoading) {
            binding!!.progressBar.visibility = View.VISIBLE
        } else {
            binding!!.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val LOGIN = "login"
    }


}