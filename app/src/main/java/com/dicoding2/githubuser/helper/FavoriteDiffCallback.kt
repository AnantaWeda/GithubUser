package com.dicoding2.githubuser.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding2.githubuser.database.Favorite

class FavoriteDiffCallback(private val oldFavoriteList: List<Favorite>, private val newFavoriteList: List<Favorite>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavoriteList.size
    override fun getNewListSize(): Int = newFavoriteList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavoriteList[oldItemPosition].name == newFavoriteList[newItemPosition].name
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldFavoriteList[oldItemPosition]
        val newNote = newFavoriteList[newItemPosition]
        return oldNote.login == newNote.login && oldNote.avatarUrl == newNote.avatarUrl
    }
}