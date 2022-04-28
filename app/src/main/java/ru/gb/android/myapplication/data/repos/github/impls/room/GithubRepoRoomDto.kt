package ru.gb.android.myapplication.data.repos.github.impls.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repos_table")
data class GithubRepoRoomDto(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "owner") val ownerName: String? = null
)