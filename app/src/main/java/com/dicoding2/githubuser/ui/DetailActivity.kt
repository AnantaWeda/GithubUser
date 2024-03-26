package com.dicoding2.githubuser.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding2.githubuser.R
import com.dicoding2.githubuser.adapter.SectionsPagerAdapter
import com.dicoding2.githubuser.data.response.DetailUserResponse
import com.dicoding2.githubuser.database.Favorite
import com.dicoding2.githubuser.databinding.ActivityDetailBinding
import com.dicoding2.githubuser.viewmodel.DetailViewModel
import com.dicoding2.githubuser.viewmodel.FactoryViewModel
import com.dicoding2.githubuser.viewmodel.FavoriteViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var isDelete: Boolean = false

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getStringExtra(EXTRA_USER)

        val mainViewModel = obtainViewModel(this@DetailActivity)
        mainViewModel.findFavorite(user ?: "").observe(this) { userFavorite ->
            setColorFavorite(userFavorite != null)
            isDelete = userFavorite != null
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailViewModel::class.java)

        favoriteViewModel = obtainViewModel(this@DetailActivity)
        detailViewModel.getDetailUser(user.toString())

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.detailGithubUser.observe(this) { userData ->
            setDetailUserData(userData)
            val obj = Favorite(
                login = userData.login ?: "",
                avatarUrl = userData.avatarUrl ?: "",
                name = userData.name ?: ""
            )
            binding.faFavorite.setOnClickListener{
                if(isDelete){
                    favoriteViewModel.delete(obj)
                }else{
                    favoriteViewModel.insert(obj)
                }
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter
        sectionsPagerAdapter.username = user.toString()

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setDetailUserData(data: DetailUserResponse){
        showLoading(false)
        Glide.with(this).load(data.avatarUrl).into(binding.ivProfile)
        binding.tvUsername.text = data.login
        binding.tvName.text = data.name
        binding.tvFollowers.text = "Followers : ${data.followers.toString()}"
        binding.tvFollowing.text = "Following : ${data.following.toString()}"

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = FactoryViewModel.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    private fun setColorFavorite(status: Boolean){
        if(status){
            binding.faFavorite.setColorFilter(ContextCompat.getColor(this, android.R.color.holo_red_light))
        }else{
            binding.faFavorite.setColorFilter(ContextCompat.getColor(this, android.R.color.darker_gray))
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val EXTRA_USER = "extra_user"
    }
}