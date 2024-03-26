package com.dicoding2.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding2.githubuser.data.response.DetailUserResponse
import com.dicoding2.githubuser.database.Favorite
import com.dicoding2.githubuser.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllUserFavorite(): LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorite()

    fun findFavorite(payload: String): LiveData<Favorite> = mFavoriteRepository.findFavorite(payload)

    fun insert (favorite: Favorite){
        mFavoriteRepository.insert(favorite)
    }

    fun delete (favorite: Favorite){
        mFavoriteRepository.delete(favorite)
    }
}