package com.dicoding2.githubuser.data.response

import com.google.gson.annotations.SerializedName

data class FollowUserResponse(
	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,
)
