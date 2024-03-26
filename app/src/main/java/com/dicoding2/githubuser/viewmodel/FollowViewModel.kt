package com.dicoding2.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding2.githubuser.data.response.FollowUserResponse
import com.dicoding2.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel(){
    private val _listFollowers = MutableLiveData<List<FollowUserResponse>>()
    val listFollowers: LiveData<List<FollowUserResponse>> = _listFollowers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "FollowersViewModel"
    }

    fun getFollowersUser(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowersGithubUser(username)
        client.enqueue(object : Callback<List<FollowUserResponse>> {
            override fun onResponse(
                call: Call<List<FollowUserResponse>>,
                response: Response<List<FollowUserResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollowers.value = response.body()
                } else {
                    Log.e(FollowViewModel.TAG, "onFailure1: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<FollowUserResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(FollowViewModel.TAG, "onFailure2: ${t.message.toString()}")
            }
        })
    }

    fun getFollowingUser(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowingGithubUser(username)
        client.enqueue(object : Callback<List<FollowUserResponse>> {
            override fun onResponse(
                call: Call<List<FollowUserResponse>>,
                response: Response<List<FollowUserResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollowers.value = response.body()
                } else {
                    Log.e(FollowViewModel.TAG, "onFailure1: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<FollowUserResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(FollowViewModel.TAG, "onFailure2: ${t.message.toString()}")
            }
        })
    }
}