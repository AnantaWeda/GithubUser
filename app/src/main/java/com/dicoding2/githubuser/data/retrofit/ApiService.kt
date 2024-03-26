package com.dicoding2.githubuser.data.retrofit

import com.dicoding2.githubuser.data.response.DetailUserResponse
import com.dicoding2.githubuser.data.response.FollowUserResponse
//import com.dicoding2.githubuser.data.response.FollowingUserResponse
import com.dicoding2.githubuser.data.response.ListUserResponse
import com.dicoding2.githubuser.data.response.SearchUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/search/users")
    fun searchListGithubUser(
        @Query("q") username: String?
    ): Call<SearchUserResponse>

    @GET("/users")
    fun getListGithubUser(
    ): Call<List<ListUserResponse>>

    @GET("/users/{username}")
    fun getDetailGithubUser(
       @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("/users/{username}/followers")
    fun getFollowersGithubUser(
        @Path("username") username: String
    ): Call<List<FollowUserResponse>>

    @GET("/users/{username}/following")
    fun getFollowingGithubUser(
        @Path("username") username: String
    ): Call<List<FollowUserResponse>>
}