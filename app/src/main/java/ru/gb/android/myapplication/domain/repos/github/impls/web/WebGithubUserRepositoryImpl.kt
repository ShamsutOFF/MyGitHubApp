package ru.gb.android.myapplication.domain.repos.github.impls.web

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import ru.gb.android.myapplication.domain.entities.GithubRepoEntity
import ru.gb.android.myapplication.domain.entities.GithubUserProfileEntity
import ru.gb.android.myapplication.domain.repos.github.GithubUserRepository

class WebGithubUserRepositoryImpl(private val retrofit: Retrofit) : GithubUserRepository {
    private val service: GithubWebService by lazy { retrofit.create(GithubWebService::class.java) }

    override fun getGithubRepos(
        userName: String,
        onSuccess: (List<GithubRepoEntity>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        service.getReposForUser(userName).enqueue(object : Callback<List<GithubRepoEntity>> {
            override fun onResponse(
                call: Call<List<GithubRepoEntity>>,
                response: Response<List<GithubRepoEntity>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) }
                } else {
                    onError(Throwable("Api error ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<List<GithubRepoEntity>>, t: Throwable) {
                onError(t)
            }
        })
    }

    override fun getAvatar(
        userName: String,
        onSuccess: (String) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        service.getProfile(userName).enqueue(object : Callback<GithubUserProfileEntity> {
            override fun onResponse(
                call: Call<GithubUserProfileEntity>,
                response: Response<GithubUserProfileEntity>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it.avatarUrl) }
                } else {
                    onError(Throwable("Api error ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<GithubUserProfileEntity>, t: Throwable) {
                onError(t)
            }

        })
    }
}
