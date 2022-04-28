package ru.gb.android.myapplication.data.repos.github.impls.room

import android.os.Handler
import ru.gb.android.myapplication.data.entities.GithubRepoEntity
import ru.gb.android.myapplication.data.repos.github.GithubUserRepository

class RoomGithubUserRepositoryImpl(
    private val dao: GithubRepoDao,
    private val handler: Handler
) : GithubUserRepository {

    fun saveCache(userName: String, cache: List<GithubRepoEntity>) {
        Thread {
            dao.deleteUserRepo(userName)
            dao.insertAll(*cache.map { it.toDto(userName) }.toTypedArray())
        }.start()
    }

    override fun getGithubRepos(
        userName: String,
        onSuccess: (List<GithubRepoEntity>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Thread {
            val repos = dao.getAll(userName).map { it.toPlain() }
            handler.post { onSuccess(repos) }
        }.start()
    }

    override fun getAvatar(
        userName: String,
        onSuccess: (String) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    private fun GithubRepoEntity.toDto(userName: String) = GithubRepoRoomDto(id, name, userName)

    private fun GithubRepoRoomDto.toPlain() = GithubRepoEntity(id, name)

}