package ru.gb.android.myapplication.data.repos.github.impls

import android.os.Handler
import retrofit2.Retrofit
import ru.gb.android.myapplication.data.entities.GithubRepoEntity
import ru.gb.android.myapplication.data.repos.github.GithubUserRepository
import ru.gb.android.myapplication.data.repos.github.impls.room.GithubRepoDao
import ru.gb.android.myapplication.data.repos.github.impls.room.RoomGithubUserRepositoryImpl
import ru.gb.android.myapplication.data.repos.github.impls.web.WebGithubUserRepositoryImpl

class CombinedGithubRepositoryImpl(
    retrofit: Retrofit,
    dao: GithubRepoDao,
    handler: Handler
) : GithubUserRepository {
    private val webRepo = WebGithubUserRepositoryImpl(retrofit)
    private val cacheRepo = RoomGithubUserRepositoryImpl(dao, handler)

    override fun getGithubRepos(
        userName: String,
        onSuccess: (List<GithubRepoEntity>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        cacheRepo.getGithubRepos(userName, {
            if (it.isNotEmpty()) {
                onSuccess(it)
            }
        }, onError)

        webRepo.getGithubRepos(userName, {
            cacheRepo.saveCache(userName, it)
            onSuccess(it)
        }, onError)
    }

    override fun getAvatar(
        userName: String,
        onSuccess: (String) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        webRepo.getAvatar(userName, onSuccess, onError)
    }
}