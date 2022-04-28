package ru.gb.android.myapplication.data.repos.github.impls.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    version = 3,
    entities = [GithubRepoRoomDto::class],
)
abstract class GithubDatabaseRoom : RoomDatabase() {
    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE GithubRepoEntity RENAME TO repos_table;"
                )
            }
        }
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE repos_table ADD COLUMN owner TEXT"
                )
            }
        }
    }

    abstract fun githubRepoDao(): GithubRepoDao
}