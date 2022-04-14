package ru.gb.android.myapplication.domain.repos.github.impls.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GithubRepoDao {
    @Query("SELECT * FROM repos_table WHERE owner == :userName")
    fun getAll(userName: String): List<GithubRepoRoomDto>

    @Insert
    fun insertAll(vararg repos: GithubRepoRoomDto)

    @Query("DELETE FROM repos_table WHERE owner == :userName")
    fun deleteUserRepo(userName: String)
}