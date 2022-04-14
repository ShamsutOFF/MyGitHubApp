package ru.gb.android.myapplication.domain.repos.github

import ru.gb.android.myapplication.domain.entities.GithubRepoEntity


interface GithubUserRepository {
    fun getGithubRepos(
        userName: String,
        onSuccess: (List<GithubRepoEntity>) -> Unit,
        onError: (Throwable) -> Unit
    )

    fun getAvatar(
        userName: String,
        onSuccess: (String) -> Unit,
        onError: (Throwable) -> Unit
    )
}