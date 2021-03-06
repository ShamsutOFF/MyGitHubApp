package ru.gb.android.myapplication.data.repos.github.impls.web

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.gb.android.myapplication.data.entities.GithubRepoEntity
import ru.gb.android.myapplication.data.entities.GithubUserProfileEntity

interface GithubWebService {
    @GET("users/{user}/repos")
    fun getReposForUser(@Path("user") userName: String): Call<List<GithubRepoEntity>>

    @GET("users/{user}")
    fun getProfile(@Path("user") userName: String): Call<GithubUserProfileEntity>
}