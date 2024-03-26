package com.dicoding2.githubuser.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding2.githubuser.R
import com.dicoding2.githubuser.adapter.ListUserFollowAdapter
import com.dicoding2.githubuser.data.response.FollowUserResponse
import com.dicoding2.githubuser.viewmodel.FollowViewModel


class FollowFragment : Fragment() {

    private lateinit var rvFollowers: RecyclerView
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var position: Int = 0

        arguments?.let {
            position = it.getInt(ARG_POSITION,0)
            username = it.getString(ARG_USERNAME).toString()
        }

        rvFollowers = view.findViewById(R.id.rvFollowers)

        val layoutManager = LinearLayoutManager(requireActivity())
        rvFollowers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        rvFollowers.addItemDecoration(itemDecoration)

        val followViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowViewModel::class.java)

        if(position == 1){
            followViewModel.getFollowersUser(username)
        }else{
            followViewModel.getFollowingUser(username)
        }
        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it, view)
        }

        followViewModel.listFollowers.observe(viewLifecycleOwner) { userData ->
            setFollowersUserData(userData)
        }
    }
    private fun setFollowersUserData(listUser: List<FollowUserResponse>) {
        val adapter = ListUserFollowAdapter()
        adapter.submitList(listUser)
        rvFollowers.adapter = adapter
    }
    private fun showLoading(isLoading: Boolean, view: View) {
        view.findViewById<ProgressBar>(R.id.progressBar).visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_POSITION: String = "arg_position"
        const val ARG_USERNAME: String = "arg_username"

    }
}