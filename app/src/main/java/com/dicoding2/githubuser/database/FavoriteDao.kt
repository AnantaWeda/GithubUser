package com.dicoding2.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dicoding2.githubuser.data.response.DetailUserResponse

@Dao
interface FavoriteDao {
    @Query("SELECT * from favorite")
    fun getAllFavorites(): LiveData<List<Favorite>>

    @Query("SELECT * from favorite WHERE login = :payload")
    fun findFavorite(payload: String): LiveData<Favorite>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)

    @Update
    fun update(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)
}