package com.dicoding2.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding2.githubuser.R
import com.dicoding2.githubuser.adapter.FavoriteAdapter
import com.dicoding2.githubuser.data.response.ListUserResponse
import com.dicoding2.githubuser.database.Favorite
import com.dicoding2.githubuser.databinding.ActivityFavoriteBinding
import com.dicoding2.githubuser.viewmodel.FactoryViewModel
import com.dicoding2.githubuser.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private var _activityFavoriteBinding: ActivityFavoriteBinding? = null
    private val binding get() = _activityFavoriteBinding

    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val mainViewModel = obtainViewModel(this@FavoriteActivity)
        mainViewModel.getAllUserFavorite().observe(this) { userFavorite ->
            if (userFavorite != null) {
                adapter.setListNotes(userFavorite)
            }
        }

        adapter = FavoriteAdapter()

        adapter.setOnItemClickCallback(object: FavoriteAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Favorite) {
                val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USER, data.login)
                startActivity(intent)
            }
        })

        binding?.rvListUserGithubFavorite?.layoutManager = LinearLayoutManager(this)
        binding?.rvListUserGithubFavorite?.setHasFixedSize(false)
        binding?.rvListUserGithubFavorite?.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = FactoryViewModel.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteBinding = null
    }
}