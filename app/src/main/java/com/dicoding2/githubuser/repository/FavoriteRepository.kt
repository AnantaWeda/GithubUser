package com.dicoding2.githubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding2.githubuser.data.response.DetailUserResponse
import com.dicoding2.githubuser.database.Favorite
import com.dicoding2.githubuser.database.FavoriteDao
import com.dicoding2.githubuser.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorite(): LiveData<List<Favorite>> = mFavoriteDao.getAllFavorites()

    fun findFavorite(payload: String): LiveData<Favorite> = mFavoriteDao.findFavorite(payload)

    fun insert(favorite: Favorite) {
        executorService.execute { mFavoriteDao.insert(favorite) }
    }

    fun delete(favorite: Favorite) {
        executorService.execute { mFavoriteDao.delete(favorite) }
    }
}