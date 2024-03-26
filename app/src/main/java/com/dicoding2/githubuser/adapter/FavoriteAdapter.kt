package com.dicoding2.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding2.githubuser.database.Favorite
import com.dicoding2.githubuser.databinding.ItemListUserBinding
import com.dicoding2.githubuser.helper.FavoriteDiffCallback

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private lateinit var setOnItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.setOnItemClickCallback = onItemClickCallback
    }

    private val listFavorite = ArrayList<Favorite>()
    fun setListNotes(listNotes: List<Favorite>) {
        val diffCallback = FavoriteDiffCallback(this.listFavorite, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorite.clear()
        this.listFavorite.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
        holder.itemView.setOnClickListener {
            setOnItemClickCallback.onItemClicked(listFavorite[position])
        }
    }
    override fun getItemCount(): Int {
        return listFavorite.size
    }
    inner class FavoriteViewHolder(private val binding: ItemListUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            with(binding) {
                Glide.with(cardViewListUser)
                    .load(favorite.avatarUrl)
                    .into(imgItemPhoto)
                tvItemName.text = favorite.login
            }
        }
    }
    interface OnItemClickCallback{
        fun onItemClicked(data: Favorite)
    }
}