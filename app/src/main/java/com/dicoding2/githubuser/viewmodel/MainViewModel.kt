package com.dicoding2.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding2.githubuser.data.response.ListUserResponse
import com.dicoding2.githubuser.data.response.SearchUserResponse
import com.dicoding2.githubuser.data.retrofit.ApiConfig
import com.dicoding2.githubuser.preference.SettingPreferences
import com.dicoding2.githubuser.util.Event
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel() : ViewModel() {
    private val _listGithubUser = MutableLiveData<List<ListUserResponse>>()
    val listGithubUser: LiveData<List<ListUserResponse>> = _listGithubUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    init {
        listGithubUser()
    }

    private fun listGithubUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListGithubUser()
        client.enqueue(object : Callback<List<ListUserResponse>> {
            override fun onResponse(
                call: Call<List<ListUserResponse>>,
                response: Response<List<ListUserResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listGithubUser.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ListUserResponse>>, t: Throwable) {

                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun searchGithubUser(username: String) {
        _isLoading.value = true
        if(username.isEmpty()){
            listGithubUser()
            return
        }
        val client = ApiConfig.getApiService().searchListGithubUser(username)
        client.enqueue(object : Callback<SearchUserResponse> {
            override fun onResponse(
                call: Call<SearchUserResponse>,
                response: Response<SearchUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _listGithubUser.value = responseBody?.items
                    if(responseBody?.totalCount == 0){
                        _toastText.value = Event("Tidak ada data")
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
    fun getThemeSettings(pref: SettingPreferences): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(pref: SettingPreferences, isDarkMModeActive: Boolean){
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkMModeActive)
        }
    }
    companion object{
        private const val TAG = "MainViewModel"
    }
}