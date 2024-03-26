package com.dicoding2.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding2.githubuser.R
import com.dicoding2.githubuser.adapter.ListGithubUserAdapter
import com.dicoding2.githubuser.data.response.ListUserResponse
import com.dicoding2.githubuser.databinding.ActivityMainBinding
import com.dicoding2.githubuser.preference.SettingPreferences
import com.dicoding2.githubuser.preference.dataStore
import com.dicoding2.githubuser.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private var isDarkMode: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java
        )

        val layoutManager = LinearLayoutManager(this)
        binding.rvListUserGithub.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvListUserGithub.addItemDecoration(itemDecoration)

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.listGithubUser.observe(this) { userData ->
            setGithubUserData(userData)
        }

        supportActionBar?.hide()
        mainViewModel.getThemeSettings(pref).observe(this){isDarkModeActive: Boolean ->
            if (isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                isDarkMode = true
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                isDarkMode = false
            }
            val settingsMenuItem = binding.searchBar.menu.findItem(R.id.itSettings)
            val favoriteMenuItem = binding.searchBar.menu.findItem(R.id.itFavoriteList)
            val iconDrawable = ContextCompat.getDrawable(this, R.drawable.ic_favorite)
            if (isDarkModeActive) {
                settingsMenuItem.setIcon(R.drawable.ic_light)
                iconDrawable?.let { DrawableCompat.setTint(it, ContextCompat.getColor(this, R.color.white)) }
                favoriteMenuItem.setIcon(iconDrawable)
            } else {
                settingsMenuItem.setIcon(R.drawable.ic_night)
                iconDrawable?.let { DrawableCompat.setTint(it, ContextCompat.getColor(this, R.color.black)) }
                favoriteMenuItem.setIcon(iconDrawable)
            }

        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    mainViewModel.searchGithubUser(searchView.text.toString())

                    mainViewModel.isLoading.observe(this@MainActivity) {
                        showLoading(it)
                    }

                    mainViewModel.listGithubUser.observe(this@MainActivity) { userData ->
                        setGithubUserData(userData)
                    }

                    mainViewModel.toastText.observe(this@MainActivity) {
                        it.getContentIfNotHandled()?.let { toastText ->
                            Toast.makeText(
                                this@MainActivity,
                                toastText,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    false
                }
        }
        binding.searchBar.inflateMenu(R.menu.main_menu)
        binding.searchBar.setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.itFavoriteList -> {
                    val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.itSettings -> {
                    mainViewModel.saveThemeSetting(pref,!isDarkMode)
                    true
                }
                else -> false
            }
        }
    }


    private fun setGithubUserData(listUser: List<ListUserResponse>) {
        val adapter = ListGithubUserAdapter()
        adapter.submitList(listUser)
        binding.rvListUserGithub.adapter = adapter

        adapter.setOnItemClickCallback(object: ListGithubUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ListUserResponse) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USER, data.login)
                startActivity(intent)
            }
        })
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}