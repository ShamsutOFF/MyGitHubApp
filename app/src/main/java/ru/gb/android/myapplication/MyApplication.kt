package ru.gb.android.myapplication

import android.app.Application
import android.os.Handler
import androidx.room.Room
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.gb.android.myapplication.domain.repos.github.impls.CombinedGithubRepositoryImpl
import ru.gb.android.myapplication.domain.repos.github.impls.room.GithubDatabaseRoom

private const val BASE_URL = "https://api.github.com/"

class MyApplication : Application() {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val db by lazy {
        Room
            .databaseBuilder(
                applicationContext,
                GithubDatabaseRoom::class.java, "github-db"
            )
            .addMigrations(
                GithubDatabaseRoom.MIGRATION_1_2,
                GithubDatabaseRoom.MIGRATION_2_3
            )
            .build()
    }

    val githubUserRepo by lazy {
        CombinedGithubRepositoryImpl(retrofit, db.githubRepoDao(), Handler(mainLooper))
    }

}