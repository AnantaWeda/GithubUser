package com.dicoding2.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding2.githubuser.data.response.FollowUserResponse
import com.dicoding2.githubuser.databinding.ItemListUserBinding

class ListUserFollowAdapter : ListAdapter<FollowUserResponse, ListUserFollowAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }

    class MyViewHolder(val binding: ItemListUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FollowUserResponse){
            Glide.with(binding.cardViewListUser)
                .load(user.avatarUrl)
                .into(binding.imgItemPhoto)
            binding.tvItemName.text = user.login
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FollowUserResponse>() {
            override fun areItemsTheSame(oldItem: FollowUserResponse, newItem: FollowUserResponse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: FollowUserResponse,
                newItem: FollowUserResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}