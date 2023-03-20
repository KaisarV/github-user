package com.kai.githubuser

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kai.githubuser.adapter.FollowAdapter
import com.kai.githubuser.databinding.FragmentFollowerBinding
import com.kai.githubuser.response.FollowResponseItem
import com.kai.githubuser.viewmodel.FollowViewModel


class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowerBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }
    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentFollowerBinding.inflate(layoutInflater)
        val followViewModel =
            ViewModelProvider(requireActivity())[FollowViewModel::class.java]

        followViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        followViewModel.listFollower.observe(this) { followers ->
            setFollowers(followers)
        }
    }

    private fun setFollowers(users: List<FollowResponseItem>) {
        Log.d("TES1", "ADAA")
        val listFollowers = ArrayList<FollowResponseItem>()

        for (user in users) {
            listFollowers.add(
                user
            )
        }
        Log.d("TES2", "ADAA")
        val listFollowerAdapter = FollowAdapter(listFollowers)

//        listUserAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: ItemsItem) {
//                moveProfile(data)
//            }
//        })

        binding.rvUser2.adapter = listFollowerAdapter
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val tvLabel: TextView = view.findViewById(R.id.section_label)
//        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
//        tvLabel.text = getString(R.string.content_tab_text, index)
//    }


    private fun showLoading(isLoading: Boolean) {

        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
    }


}