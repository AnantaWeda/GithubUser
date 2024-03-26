package com.dicoding2.githubuser.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Favorite (
    @PrimaryKey(autoGenerate = false)
    var login: String = "",
    var avatarUrl: String = "",
    var name: String = ""
)