package ru.gb.android.myapplication.domain.entities

import com.google.gson.annotations.SerializedName

data class GithubUserProfileEntity(
    val login: String,
    val id: Int,
    @SerializedName("avatar_url")
    val avatarUrl: String
)